package com.italomonitor.main.components;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

@Component
public class CRCCalculator {
	
	public String calculaCRC( String dividendo, String divisor ) {		
		char[] dividendoVet = dividendo.toCharArray();
		char[] divisorVet = divisor.toCharArray();
				
		int i = this.indiceDoPrimUm( dividendoVet );
		int len = quantDeDigitosSignificativos( dividendoVet, i );
		
		while ( len >= divisorVet.length ) {
			for( int j = 0; j < divisorVet.length; j++ ) {
				if ( dividendoVet[ i+j ] != divisorVet[ j ] )
					dividendoVet[ i+j ] = '1';				
				else dividendoVet[ i+j ] = '0';
			}
			
			i = indiceDoPrimUm( dividendoVet );
			len = quantDeDigitosSignificativos( dividendoVet, i );
		}
		
		return this.binToHex( dividendoVet );
	}
		
	public String binToHex( char[] bin ) {
		StringBuilder sb = new StringBuilder();
		
		boolean achouUm = false;
		for( char ch : bin ) {
			int value = Integer.parseInt( ""+ch );
			if ( !achouUm )
				if ( value == 1 )
					achouUm = true;
			
			if ( achouUm )
				sb.append( value );
		}
		
		int value = Integer.parseInt( sb.toString(), 2 );
		
		return Integer.toHexString( value );
	}
	
	public int quantDeDigitosSignificativos( char[] bin, int indiceDoPrimUm ) {
		if ( indiceDoPrimUm != -1 )
			return bin.length - indiceDoPrimUm;
		return 0;
	}
	
	public int indiceDoPrimUm( char[] bin ) {
		boolean achouUm = false;
		int i = 0;
		while( !achouUm && i < bin.length ) {
			if ( bin[ i ] == '1' )
				achouUm = true;
			else i++;
		}
		if ( !achouUm )
			return -1;
		return i;
	}
	
	public String strToBin( String str ) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		
		byte[] bytes = str.getBytes( "UTF-8" );
		for( byte b : bytes ) {
			for( int i = 0; i < 8; i++ ) {
				sb.append( ( b & 0x80 ) == 0 ? 0 : 1 );
				b <<= 1;
			}
		}
		
		return sb.toString();
	}
		
	public static void main( String[] args ) {
		CRCCalculator calc = new CRCCalculator();
		String payload = "00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a4564266554400005204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***6304";		
		
		try {
			String dividendo = calc.strToBin( payload ) + "0".repeat( 12 );
			String divisor = "1000000100001";
			
			System.out.println( calc.calculaCRC( dividendo, divisor ) );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
		
}
