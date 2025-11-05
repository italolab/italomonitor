package com.redemonitor.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class RedeMonitorMainApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(RedeMonitorMainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {				
		//String[] roles = { "microservice" };
		//int expiration = Integer.MAX_VALUE;
		//System.out.println( jwtTokenUtil.createAccessToken( "microservice", roles, expiration ) );
	}

}
