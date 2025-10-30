package com.redemonitor.api_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class GatewayFilterFactory2 extends AbstractGatewayFilterFactory<String> {

	public GatewayFilterFactory2() {
		super( String.class );
	}
	
	@Override
	public GatewayFilter apply( String config ) {
		return (exchange, chain) -> {
			System.out.println( "PATH= "+ exchange.getRequest().getPath().value() );
			System.out.println( exchange.getRequest().getURI() );
			System.out.println( exchange.getResponse().getCookies().containsKey( "access_token" ) );
			return chain.filter( exchange );
		};
	}
	

}
