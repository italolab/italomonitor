package com.redemonitor.main.messaging.email;

import java.util.Properties;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EMailSender {
		
	@Value("${mail.address.bot}")
	private String sistemaEMail;
	
	@Value("${mail.address.bot.password}")
	private String password;
	
	@Value("${mail.server.host}")
	private String host;
	
	@Value("${mail.server.port}")
	private String port;

	public void sendEMail( String to, String subject, String html ) {
		Properties props = new Properties();
		props.put( "mail.smtp.host", host );
		props.put( "mail.smtp.port", port );
		//props.put( "mail.debug", "true" );
		
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.ssl.enable", "true" );
		props.put( "mail.smtp.ssl.trust", "localhost" );
		props.put( "mail.smtp.socketFactory.port", "465" );
		props.put( "mail.smtp.socketFactory.class", "java.net.ssl.SSLSocketFactory" );
		props.put( "mail.smtp.starttls.enable", "false" );
				
		Session session = Session.getInstance( props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication( sistemaEMail, password );
			}
			
		} );
		
		try {
			MimeMessage message = new MimeMessage( session );		
			message.setFrom( new InternetAddress( sistemaEMail ) );
			message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( to ) );
			message.setSubject( subject );
			message.setContent( html, "text/html; charset=UTF-8" ); 
		
			Transport.send( message );
		} catch ( MessagingException e ) {
			Logger.getLogger( EMailSender.class ).error( "Não foi possível enviar a mensagem de email de "+sistemaEMail+" para "+to+"\nErro= "+e.getMessage() );
		}
	}
			
}



/*
	public static void main( String[] args ) {
		String sistemaEMail = "bot@italomonitor.com.br";
		String password = "22101988";
		
		String to = "italoherbert@outlook.com";
		String subject = "Teste";
		String text = "Testando...";
		
		Properties props = new Properties();
		props.put( "mail.smtp.host", "localhost" );
		props.put( "mail.smtp.port", "465" );
		props.put( "mail.debug", "true" );
		
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.ssl.enable", "true" );
		props.put( "mail.smtp.ssl.trust", "localhost" );
		props.put( "mail.smtp.socketFactory.port", "465" );
		props.put( "mail.smtp.socketFactory.class", "java.net.ssl.SSLSocketFactory" );
		props.put( "mail.smtp.starttls.enable", "false" );
		
								
		Session session = Session.getInstance( props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication( sistemaEMail, password );
			}
			
		} );

		try {
			MimeMessage message = new MimeMessage( session );		
			message.setFrom( new InternetAddress( sistemaEMail ) );
			message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( to ) );
			message.setSubject( subject );
			message.setContent( text, "text/html; charset=UTF-8" );
		
			Transport.send( message );
			
			System.out.println( "MENSAGEM ENVIADA!" );
		} catch ( MessagingException e ) {
			Logger.getLogger( EMailSender.class ).error( "Não foi possível enviar a mensagem de email de "+sistemaEMail+" para "+to );
			e.printStackTrace();
		}
	}

 */
