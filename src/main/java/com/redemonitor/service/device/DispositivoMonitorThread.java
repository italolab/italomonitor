package com.redemonitor.service.device;

import com.redemonitor.model.Config;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.model.Empresa;
import com.redemonitor.model.enums.DispositivoStatus;
import com.redemonitor.repository.DispositivoRepository;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispositivoMonitorThread implements Runnable {

    private Dispositivo dispositivo;
    private Config config;
    private DispositivoRepository dispositivoRepository;

    public DispositivoMonitorThread( Dispositivo dispositivo, Config config, DispositivoRepository dispositivoRepository ) {
        this.dispositivo = dispositivo;
        this.config = config;
        this.dispositivoRepository = dispositivoRepository;
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

            Scanner scanner = new Scanner(proc.getInputStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.trim();

                String pedidosStr = "Perdidos = ";
                int pedidosStrLen = pedidosStr.length();

                if ( line.startsWith( "Pacotes" ) ) {
                    int j = line.indexOf( pedidosStr );
                    if ( j == -1 || j + pedidosStrLen > line.length() ) {
                        String msg = "Falha na leitura da saída do comando ping.\nAjustar o interpretador da saída do comando!";
                        Logger.getLogger( DispositivoMonitorThread.class.getName()).log(Level.SEVERE, msg );
                    }

                    char perdidosValue = line.charAt( j+pedidosStrLen );
                    int quantPerdidos = Integer.parseInt( ""+perdidosValue );

                    boolean statusAlterado = false;
                    if ( quantPerdidos >= porcentagemMaxFalhasPorLote*numPacotesPorLote ) {
                        if ( dispositivo.getStatus() == DispositivoStatus.ATIVO )
                            statusAlterado = true;
                    } else {
                        if ( dispositivo.getStatus() == DispositivoStatus.INATIVO )
                            statusAlterado = true;
                    }

                    if ( statusAlterado ) {
                        dispositivo.setStatus( dispositivo.getStatus() == DispositivoStatus.ATIVO ? DispositivoStatus.INATIVO : DispositivoStatus.ATIVO );
                        dispositivoRepository.save( dispositivo );
                    }
                }
            }

            try {
                proc.waitFor();
            } catch (InterruptedException ex) {

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
