package italo.italomonitor.main.security;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {

	private String authorizationHeader = null;
	
	public MutableHttpServletRequest( HttpServletRequest request ) {
		super( request );
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader( name );
		if ( value != null )
			return value;
		if ( name != null )
			if ( name.equals( "Authorization" ) )
				return authorizationHeader;		
		return null;
	}

	@Override
	public Enumeration<String> getHeaders( String name ) {
		Set<String> values = new HashSet<>();
		
		Enumeration<String> headerValues = super.getHeaders( name );		
		
		boolean addAuthorization = true;
		while( headerValues.hasMoreElements() ) {
			String headerValue = headerValues.nextElement();
			values.add( headerValue );
			
			if ( headerValue.equals( "Authorization" ) )
				addAuthorization = false;
		}
		
		if ( addAuthorization ) {
			if ( name != null )
				if ( name.equals( "Authorization" ) )
					values.add( authorizationHeader );
		}
		
		return Collections.enumeration( values ); 
	}
	
	@Override
	public Enumeration<String> getHeaderNames() {
		Set<String> values = new HashSet<>();
		
		Enumeration<String> headerValues = super.getHeaderNames();
		
		boolean addAuthorization = true;
		while( headerValues.hasMoreElements() ) {
			String headerValue = headerValues.nextElement();			
			values.add( headerValue );
			
			if ( headerValue.equals( "Authorization" ) )
				addAuthorization = false;
		}
		
		if ( addAuthorization )
			values.add( authorizationHeader );
		
		return Collections.enumeration( values );
	}
	
	public void setAuthorizationHeader( String authorizationHeader ) {
		this.authorizationHeader = authorizationHeader;
	}
	
}
