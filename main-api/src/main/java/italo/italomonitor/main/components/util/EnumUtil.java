package italo.italomonitor.main.components.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.response.EnumOptionResponse;
import italo.italomonitor.main.enums.EnumOption;

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
