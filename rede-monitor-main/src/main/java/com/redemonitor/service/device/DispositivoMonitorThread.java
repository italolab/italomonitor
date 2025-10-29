package com.redemonitor.service.device;

import com.redemonitor.model.Config;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.model.Empresa;
import com.redemonitor.model.Evento;
import com.redemonitor.model.enums.DispositivoStatus;
import com.redemonitor.repository.DispositivoRepository;
import com.redemonitor.repository.EventoRepository;
import com.redemonitor.service.message.DispositivoMessageService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispositivoMonitorThread implements Runnable {

    private Dispositivo dispositivo;
    private Config config;
    private final DispositivoRepository dispositivoRepository;
    private final DispositivoMessageService dispositivoMessageService;
    private final EventoRepository eventoRepository;
    private final String username;

    private int sucessosQuantTotal = 0;
    private int falhasQuantTotal = 0;
    private int quedasQuantTotal = 0;
    private int tempoInatividadeTotal = 0;
    private LocalDateTime ultimoRegistroEventoEm = LocalDateTime.now();
    private LocalDateTime ultimoTempoInatividadeIni = LocalDateTime.now();

    public DispositivoMonitorThread( Dispositivo dispositivo,
                                     Config config,
                                     DispositivoRepository dispositivoRepository,
                                     EventoRepository eventoRepository,
                                     DispositivoMessageService dispositivoMessageService,
                                     String username ) {
        this.dispositivo = dispositivo;
        this.config = config;
        this.dispositivoRepository = dispositivoRepository;
        this.eventoRepository = eventoRepository;
        this.dispositivoMessageService = dispositivoMessageService;
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

        String[] comando = { "ping", "-n", ""+numPacotesPorLote, host };

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

                if ( ( line.startsWith( "Resposta") && !line.contains("tempo") ) ||
                        line.startsWith( "Esgotado") || line.startsWith( "A") || line.startsWith( "Falha" ) ) {
                    quantFalhas++;
                }

                if ( line.startsWith( "Resposta") && line.contains("tempo") )
                    quantSucessos++;

                if ( quantFalhas <= maxFalhas )
                    System.out.println( line );

                if ( quantFalhas >= maxFalhas || quantSucessos >= maxSucessos)
                    proc.destroy();
            }

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
            String msg = "Falha no monitoramento do dispositivo: " + nome;
            Logger.getLogger(DispositivoMonitorThread.class.getName()).log(Level.SEVERE, msg, e);
        }

    }

    private void mudaStatusParaAtivo() {
        Duration duration = Duration.between( ultimoTempoInatividadeIni, LocalDateTime.now() );
        tempoInatividadeTotal += (int) duration.getSeconds();

        dispositivo.setStatus( DispositivoStatus.ATIVO );
        dispositivoRepository.save( dispositivo );

        dispositivoMessageService.send( dispositivo, username );
    }

    private void mudaStatusParaInativo() {
        ultimoTempoInatividadeIni = LocalDateTime.now();
        quedasQuantTotal++;

        dispositivo.setStatus( DispositivoStatus.INATIVO );
        dispositivoRepository.save( dispositivo );

        dispositivoMessageService.send( dispositivo, username );
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
                .build();

        evento.setDispositivo( dispositivo );
        eventoRepository.save( evento );

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
