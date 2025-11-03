package com.redemonitor.disp_monitor.service.device;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.redemonitor.disp_monitor.enums.DispositivoStatus;
import com.redemonitor.disp_monitor.integration.DispositivoIntegration;
import com.redemonitor.disp_monitor.integration.EventoIntegration;
import com.redemonitor.disp_monitor.messaging.DispositivoMessageService;
import com.redemonitor.disp_monitor.model.Config;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.model.Empresa;
import com.redemonitor.disp_monitor.model.Evento;

public class DispositivoMonitorThread implements Runnable {
	
    private Dispositivo dispositivo;
    private Config config;
    private final DispositivoIntegration dispositivoIntegration;
    private final DispositivoMessageService dispositivoWebSocketIntegration;
    private final EventoIntegration eventoIntegration;
    private final String username;

    private int sucessosQuantTotal = 0;
    private int falhasQuantTotal = 0;
    private int quedasQuantTotal = 0;
    private int tempoInatividadeTotal = 0;
    private LocalDateTime ultimoRegistroEventoEm = LocalDateTime.now();
    private LocalDateTime ultimoTempoInatividadeIni = LocalDateTime.now();

    public DispositivoMonitorThread( Dispositivo dispositivo,
                                     Config config,
                                     DispositivoIntegration dispositivoIntegration,
                                     EventoIntegration eventoIntegration,
                                     DispositivoMessageService dispositivoMessageService,
                                     String username ) {
        this.dispositivo = dispositivo;
        this.config = config;
        this.dispositivoIntegration = dispositivoIntegration;
        this.eventoIntegration = eventoIntegration;
        this.dispositivoWebSocketIntegration = dispositivoMessageService;
        this.username = username;
    }

    public void run() {
        String host;
        String nome;
        int numPacotesPorLote;
        double porcentagemMaxFalhasPorLote;
        int registroEventoPeriodo;
        Empresa empresa = dispositivo.getEmpresa();

        synchronized ( this ) {
            host = dispositivo.getHost();
            nome = dispositivo.getNome();

            porcentagemMaxFalhasPorLote = empresa.getPorcentagemMaxFalhasPorLote();

            numPacotesPorLote = config.getNumPacotesPorLote();
            registroEventoPeriodo = config.getRegistroEventoPeriodo();
        }

        String countParam = "-c";
        String os = System.getProperty( "os.name" );
        if ( os != null )
        	if ( os.toLowerCase().contains( "win" ) || os.toLowerCase().contains( "windows") ) 
        		countParam = "-n";
        
        String[] comando = { "ping", countParam, ""+numPacotesPorLote, host };

        try {
        	ProcessBuilder pb = new ProcessBuilder(comando);
            Process proc = pb.start();
            
            int maxFalhas = (int)Math.round( porcentagemMaxFalhasPorLote * numPacotesPorLote );
            int maxSucessos = numPacotesPorLote - maxFalhas;
            int quantFalhas = 0;
            int quantSucessos = 0;

            Scanner scanner = new Scanner( proc.getInputStream() );
            while ( quantFalhas < maxFalhas && scanner.hasNextLine() ) {
                String line = scanner.nextLine();

                if ( line.startsWith( "ping" ) || 
                        line.contains( "Unreachable" ) || 
                    	line.contains( "timeout" ) || 
                    	line.contains( "unknown" ) ||                    	
                        line.startsWith( "Esgotado" ) || 
                        line.startsWith( "A" ) || 
                        line.startsWith( "Falha" ) ||
                        ( line.startsWith( "Resposta" ) && !line.contains( "tempo" ) ) ) {
                    quantFalhas++;
                }

                if ( ( line.startsWith( "Resposta") && line.contains("tempo") ) || 
                		( line.contains( "icmp_seq") && line.contains( "ttl" ) && line.contains( "time" ) ) ) {
                	
                	String timeStr = "0";
                	int i = line.indexOf( "time" );
                	int j = line.indexOf( " ", i );
                	if ( i > -1 ) {
                		timeStr = line.substring( i+5, j );
                	} else {
                		i = line.indexOf( "tempo" );
                		j = line.indexOf( " ", i );
                   		timeStr = line.substring( i+6, j );                		
                	}
                	
                	if ( timeStr.endsWith( "ms" ) )
                		timeStr = timeStr.substring( 0, timeStr.length()-2 );
                	
                	double time = 0;
                	try {
                		time = Double.parseDouble( timeStr );
                	} catch ( NumberFormatException e ) {
                		
                	}
                	
                	// DEVE SER CONFIGURADO NA TELA DE CONFIGURAÇÕES
                	if ( time < 1000 ) { 
                		quantSucessos++;
                	} else {
                		quantFalhas++;
                	}
                }

                //if ( quantFalhas <= maxFalhas )
                //    System.out.println( line );

                if ( line.contains( "transmitted" ) && 
                		line.contains( "received") && 
                		line.contains( "100%" ) ) {
                
                	quantFalhas = numPacotesPorLote;
                }
                	
                
                if ( quantFalhas >= maxFalhas || quantSucessos >= maxSucessos)
                    proc.destroy();
            }
            scanner.close();

            try {
                proc.waitFor();
            } catch (InterruptedException ex) {

            }

            if ( quantFalhas < maxFalhas ) {
                if ( dispositivo.getStatus() == DispositivoStatus.INATIVO )
                    this.mudaStatusParaAtivo();
            } else {
                if ( dispositivo.getStatus() == DispositivoStatus.ATIVO )
                    this.mudaStatusParaInativo();                
            }

            sucessosQuantTotal += quantSucessos;
            falhasQuantTotal += quantFalhas;

            Duration duration = Duration.between( ultimoRegistroEventoEm, LocalDateTime.now() );
            if ( duration.getSeconds() >= registroEventoPeriodo )
                this.registraEvento( duration );
        } catch ( IOException e ) {
        	e.printStackTrace();
            String msg = "Falha no monitoramento do dispositivo: " + nome;
            Logger.getLogger(DispositivoMonitorThread.class.getName()).log(Level.SEVERE, msg, e);
        }

    }

    private void mudaStatusParaAtivo() {
        Duration duration = Duration.between( ultimoTempoInatividadeIni, LocalDateTime.now() );
        tempoInatividadeTotal += (int) duration.getSeconds();

        dispositivo.setStatus( DispositivoStatus.ATIVO );
        dispositivoIntegration.saveDispositivo( dispositivo );

        dispositivoWebSocketIntegration.sendMessage( dispositivo, username );
    }

    private void mudaStatusParaInativo() {
        ultimoTempoInatividadeIni = LocalDateTime.now();
        quedasQuantTotal++;

        dispositivo.setStatus( DispositivoStatus.INATIVO );
        dispositivoIntegration.saveDispositivo( dispositivo );
        
        dispositivoWebSocketIntegration.sendMessage( dispositivo, username );
    }

    private void registraEvento( Duration duration ) {
        if ( dispositivo.getStatus() == DispositivoStatus.INATIVO ) {
            Duration duration2 = Duration.between( ultimoTempoInatividadeIni, LocalDateTime.now() );
            tempoInatividadeTotal += (int) duration2.getSeconds();

            ultimoTempoInatividadeIni = LocalDateTime.now();
        }

        Evento evento = Evento.builder()
                .sucessosQuant( sucessosQuantTotal )
                .falhasQuant( falhasQuantTotal )
                .quedasQuant( quedasQuantTotal )
                .tempoInatividade( tempoInatividadeTotal )
                .duracao( (int) duration.getSeconds() )
                .criadoEm( new Date() )
                .dispositivoId( dispositivo.getId() )
                .build();

        eventoIntegration.saveEvento( evento );

        sucessosQuantTotal = 0;
        falhasQuantTotal = 0;
        quedasQuantTotal = 0;
        tempoInatividadeTotal = 0;
        ultimoRegistroEventoEm = LocalDateTime.now();
    }

    public void setDispositivo( Dispositivo dispositivo ) {
        synchronized( this ) {
            this.dispositivo = dispositivo;
        }
    }

    public void setConfig( Config config ) {
        synchronized ( this ) {
            this.config = config;
        }
    }

}
