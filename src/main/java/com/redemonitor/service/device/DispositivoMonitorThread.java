package com.redemonitor.service.device;

import com.redemonitor.model.Config;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.model.Empresa;
import com.redemonitor.model.enums.DispositivoStatus;
import com.redemonitor.repository.DispositivoRepository;
import com.redemonitor.service.message.DispositivoMessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispositivoMonitorThread implements Runnable {

    private Dispositivo dispositivo;
    private Config config;
    private final DispositivoRepository dispositivoRepository;
    private final DispositivoMessageService dispositivoMessageService;
    private final String username;

    public DispositivoMonitorThread( Dispositivo dispositivo,
                                     Config config,
                                     DispositivoRepository dispositivoRepository,
                                     DispositivoMessageService dispositivoMessageService,
                                     String username ) {
        this.dispositivo = dispositivo;
        this.config = config;
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMessageService = dispositivoMessageService;
        this.username = username;
    }

    public void run() {
        String host;
        String nome;
        int numPacotesPorLote;
        double porcentagemMaxFalhasPorLote;
        Empresa empresa = dispositivo.getEmpresa();

        synchronized ( this ) {
            host = dispositivo.getHost();
            nome = dispositivo.getNome();
            porcentagemMaxFalhasPorLote = empresa.getPorcentagemMaxFalhasPorLote();
            numPacotesPorLote = config.getNumPacotesPorLote();
        }

        String[] comando = { "ping", "-n", ""+numPacotesPorLote, host };

        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process proc = pb.start();

            int maxFalhas = (int)Math.round( porcentagemMaxFalhasPorLote * numPacotesPorLote );
            int maxSucessos = numPacotesPorLote - maxFalhas;
            int quantFalhas = 0;
            int quantSucessos = 0;

            Scanner scanner = new Scanner(proc.getInputStream());
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
                if ( dispositivo.getStatus() == DispositivoStatus.INATIVO ) {
                    dispositivo.setStatus( DispositivoStatus.ATIVO );
                    dispositivoRepository.save( dispositivo );

                    dispositivoMessageService.send( dispositivo, username );
                }
            } else {
                if ( dispositivo.getStatus() == DispositivoStatus.ATIVO ) {
                    dispositivo.setStatus( DispositivoStatus.INATIVO );
                    dispositivoRepository.save( dispositivo );

                    dispositivoMessageService.send( dispositivo, username );
                }
            }
        } catch ( IOException e ) {
            String msg = "Falha no monitoramento do dispositivo: " + nome;
            Logger.getLogger(DispositivoMonitorThread.class.getName()).log(Level.SEVERE, msg, e);
        }

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
