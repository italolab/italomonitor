package com.italomonitor.main.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.italomonitor.main.components.HttpClientManager;
import com.italomonitor.main.dto.integration.request.TelegramMessageRequest;

@Component
public class TelegramIntegration {

	@Value("${telegram.message.send.endpoint}")
	private String telegramMessageSendEndpoint; 
	
	@Autowired
	private HttpClientManager httpClientManager;
	
	public void sendMessage( String botToken, String chatId, String mensagem ) {
		String uri = telegramMessageSendEndpoint.replace( "{token}", botToken );

		TelegramMessageRequest request = TelegramMessageRequest.builder()
				.chat_id( chatId )
				.text( mensagem ) 
				.build();
		
		httpClientManager.post( uri, request );
	}
	
}
