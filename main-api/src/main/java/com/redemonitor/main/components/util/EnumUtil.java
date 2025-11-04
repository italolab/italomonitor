package com.redemonitor.main.components.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.response.EnumOptionResponse;
import com.redemonitor.main.enums.EnumOption;

@Component
public class EnumUtil {
	
	public <T extends EnumOption> List<EnumOptionResponse> optionsResponse( T[] values ) {
		List<EnumOptionResponse> options = new ArrayList<>();
		for( EnumOption option : values ) {			
			options.add( 
				EnumOptionResponse.builder()
					.name( option.name() )
					.label( option.label() ) 
					.build() 
			);
		}
		return options;
	}
	
	
}
