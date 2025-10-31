package com.redemonitor.main.util;

import org.springframework.stereotype.Component;

@Component
public class BearerTokenUtil {

	 public String extractAccessToken( String authorizationHeader ) {
        if ( authorizationHeader != null )
        	if ( authorizationHeader.length() > 8 )
        		return authorizationHeader.substring( 8 );        
        return null;
    }
	
}
