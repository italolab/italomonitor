package com.redemonitor.main.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

@Configuration
public class JavaMailConfig {

	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private String port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost( host );
		mailSender.setPort( Integer.parseInt( port ) );
		mailSender.setUsername( username );
		
		Properties props = mailSender.getJavaMailProperties();
		props.put( "mail.transport.protocol", "smtp" );
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.auth.mechanisms", "XOAUTH2" );
		
		props.put( "mail.smtp.auth.login.disable", "true" );
		props.put( "mail.smtp.auth.plain.disable", "true" );
							
		Session session = Session.getDefaultInstance( props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication( username, password );
			}
			
		} );
		
		mailSender.setSession( session ); 
		
		return mailSender;
	}
	
}
