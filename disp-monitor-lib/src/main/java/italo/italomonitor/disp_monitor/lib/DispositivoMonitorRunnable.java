package italo.italomonitor.disp_monitor.lib;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import italo.italomonitor.disp_monitor.lib.enums.DispositivoStatus;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.lib.to.DispositivoState;
import italo.italomonitor.disp_monitor.lib.to.Empresa;
import italo.italomonitor.disp_monitor.lib.to.Evento;

public class DispositivoMonitorRunnable implements Runnable {
	
    private Dispositivo dispositivo;
    private Config config;
    private DispositivoMonitorListener listener;
    
    private int sucessosQuantTotal = 0;
    private int falhasQuantTotal = 0;
    private int quedasQuantTotal = 0;
    private int tempoInatividadeTotal = 0;
    private LocalDateTime ultimoRegistroEventoEm = LocalDateTime.now();
    private LocalDateTime ultimoTempoInatividadeIni = LocalDateTime.now();

    public DispositivoMonitorRunnable( Dispositivo dispositivo,
                                     Config config, DispositivoMonitorListener listener ) {
        this.dispositivo = dispositivo;
        this.config = config;       
        this.listener = listener;
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
            int latenciaMedia = 0;
            int comLatenciaQuant = 0;

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
                	
                	latenciaMedia += time;
                	comLatenciaQuant++;
                	
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
            
            
            if ( comLatenciaQuant > 0 )
            	latenciaMedia /= comLatenciaQuant;
            
            dispositivo.setLatenciaMedia( latenciaMedia );
            
            DispositivoState dispState = new DispositivoState();
            dispState.setId( dispositivo.getId() );
            dispState.setStatus( dispositivo.getStatus() );
            dispState.setLatenciaMedia( latenciaMedia ); 
            dispState.setUltimoStateEm( new Date() ); 
            
            listener.mensagemDispositivoStatusGerada( dispState ); 

            sucessosQuantTotal += quantSucessos;
            falhasQuantTotal += quantFalhas;
                        
            Duration duration = Duration.between( ultimoRegistroEventoEm, LocalDateTime.now() );
            if ( duration.getSeconds() >= registroEventoPeriodo )
                this.registraEvento( duration );            
        } catch ( IOException e ) {
            String msg = "Falha no monitoramento do dispositivo: " + nome;
            Logger.getLogger(DispositivoMonitorRunnable.class.getName()).log(Level.SEVERE, msg, e);
        }

    }

    private void mudaStatusParaAtivo() {
        Duration duration = Duration.between( ultimoTempoInatividadeIni, LocalDateTime.now() );
        tempoInatividadeTotal += (int) duration.getSeconds();

        dispositivo.setStatus( DispositivoStatus.ATIVO );
    }

    private void mudaStatusParaInativo() {
        ultimoTempoInatividadeIni = LocalDateTime.now();
        quedasQuantTotal++;

        dispositivo.setStatus( DispositivoStatus.INATIVO );        
    }

    private void registraEvento( Duration duration ) {
        if ( dispositivo.getStatus() == DispositivoStatus.INATIVO ) {
            Duration duration2 = Duration.between( ultimoTempoInatividadeIni, LocalDateTime.now() );
            tempoInatividadeTotal += (int) duration2.getSeconds();

            ultimoTempoInatividadeIni = LocalDateTime.now();
        }

        Evento evento = new Evento();
        evento.setSucessosQuant( sucessosQuantTotal );
        evento.setFalhasQuant( falhasQuantTotal );
        evento.setQuedasQuant( quedasQuantTotal );
        evento.setTempoInatividade( tempoInatividadeTotal );
        evento.setDuracao( (int) duration.getSeconds() );
        evento.setCriadoEm( new Date() );
        evento.setDispositivoId( dispositivo.getId() );                
        
        listener.mensagemEventoGerada( evento ); 

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
    
    public Config getConfig() {
    	return config;
    }
    
    public Dispositivo getDispositivo() {
    	return dispositivo;
    }

}
