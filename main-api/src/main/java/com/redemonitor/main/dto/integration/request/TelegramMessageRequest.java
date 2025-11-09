package com.redemonitor.main.dto.integration.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TelegramMessageRequest {

	private String chat_id;
	private String text;
	
}
