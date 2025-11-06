package com.redemonitor.main;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import com.redemonitor.main.components.DispositivoMonitorEscalonador;
import com.redemonitor.main.exception.ErrorException;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class RedeMonitorMainApplication implements CommandLineRunner {
	
	@Autowired
	private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;
	
	public static void main(String[] args) {
		SpringApplication.run(RedeMonitorMainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			String resp = dispositivoMonitorEscalonador.startAllMonitoramentos();
			Logger.getLogger( RedeMonitorMainApplication.class ).info( resp ); 
		} catch ( ErrorException e ) {
			Logger.getLogger( RedeMonitorMainApplication.class ).error( e.response().getMessage(), e );
		}
		
		//String[] roles = { "microservice" };
		//int expiration = Integer.MAX_VALUE;
		//System.out.println( jwtTokenUtil.createAccessToken( "microservice", roles, expiration ) );
	}

}
