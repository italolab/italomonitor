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

	//@Autowired
	//private JavaMailSender mailSender;
		
	@Value("${spring.mail.username}")
	private String sistemaEMail;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private String port;

	public void sendEMail( String to, String subject, String text ) {
		Properties props = new Properties();
		props.put( "mail.smtp.host", host );
		props.put( "mail.smtp.port", port );
		props.put( "mail.smtp.username", sistemaEMail );
		
		props.put( "mail.transport.protocol", "smtp" );
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.auth.mechanisms", "XOAUTH2" );
		
		props.put( "mail.smtp.auth.login.disable", "true" );
		props.put( "mail.smtp.auth.plain.disable", "true" );
				
		Session session = Session.getDefaultInstance( props, new Authenticator() {

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
			message.setText( text ); 
		
			Transport.send( message );
		} catch ( MessagingException e ) {
			Logger.getLogger( EMailSender.class ).error( "Não foi possível enviar a mensagem de email de "+sistemaEMail+" para "+to+"\nErro= "+e.getMessage() );
		}
	}
	
	public static void main( String[] args ) {
		String sistemaEMail = "italomonitor@outlook.com";
		String password = "orbrbaivqgtdjkik";
		
		String to = "italoherbert@outlook.com";
		String subject = "Teste";
		String text = "Testando...";
		
		Properties props = new Properties();
		props.put( "mail.smtp.host", "smtp.office365.com" );
		props.put( "mail.smtp.port", 587 );
		props.put( "mail.debug", "true" );
		
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.auth.mechanisms", "XOAUTH2" );
		
		props.put( "mail.smtp.auth.login.disable", "true" );
		props.put( "mail.smtp.auth.plain.disable", "true" );
						
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
	
}
