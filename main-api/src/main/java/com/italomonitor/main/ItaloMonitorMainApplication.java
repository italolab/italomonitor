package com.italomonitor.main;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import com.italomonitor.main.components.util.JwtTokenUtil;
import com.italomonitor.main.enums.DispositivoStatus;
import com.italomonitor.main.model.Dispositivo;
import com.italomonitor.main.model.Empresa;
import com.italomonitor.main.repository.DispositivoRepository;
import com.italomonitor.main.repository.EmpresaRepository;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class ItaloMonitorMainApplication implements CommandLineRunner {
				
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public static void main(String[] args) {
		SpringApplication.run(ItaloMonitorMainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//this.geraESalvaDispositivos();				
	}
	
	public void geraMicroserviceAccessToken() {
		String[] roles = { "microservice" };
		int expiration = Integer.MAX_VALUE;
		System.out.println( jwtTokenUtil.createAccessToken( "microservice", roles, expiration ) );
	}
	
	public void geraESalvaDispositivos() {
		String[] hosts = { "github.com", "192.168.1.1", "yahoo.com.br", "stackoverflow.com" };
		
		Empresa empresa = empresaRepository.findById( 4L ).get();
		
		for( int i = 0; i < 200; i++ ) {
			String host = hosts[ Math.abs( new Random().nextInt() ) %  hosts.length ];
						
			Dispositivo disp = Dispositivo.builder()
					.host( host )
					.nome( "disp("+i+")" )
					.descricao( "Dispositivo de teste" )
					.localizacao( "Virtual" )
					.empresa( empresa )
					.status( DispositivoStatus.INATIVO )
					.build();
			
			dispositivoRepository.save( disp );
			
			System.out.println( "Salvo disp("+i+")" );
		}
	}

}
