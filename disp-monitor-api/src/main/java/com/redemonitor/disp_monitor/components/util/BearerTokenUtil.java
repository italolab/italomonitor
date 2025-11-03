package com.redemonitor.disp_monitor.components.util;

import org.springframework.stereotype.Component;

@Component
public class BearerTokenUtil {

	 public String extractAccessToken( String authorizationHeader ) {
        if ( authorizationHeader != null )
        	if ( authorizationHeader.length() > 7 )
        		return authorizationHeader.substring( 7 );        
        return null;
    }
	
}
