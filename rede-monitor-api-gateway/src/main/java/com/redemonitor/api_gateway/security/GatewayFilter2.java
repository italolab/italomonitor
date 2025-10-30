package com.redemonitor.api_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GatewayFilter2 implements GatewayFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println( exchange.getRequest().getURI() );
		System.out.println( exchange.getResponse().getCookies().containsKey( "access_token" ) );
		return chain.filter( exchange ); 
	}

}
