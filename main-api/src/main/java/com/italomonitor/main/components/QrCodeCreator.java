package com.italomonitor.main.components;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import io.nayuki.qrcodegen.QrCode;

@Component
public class QrCodeCreator {
	
	public String genQrCodeText( String credor, String chave, String cidade, double valor ) throws UnsupportedEncodingException {    	
    	String valorStr = String.valueOf( valor );    	
    	String gui = "br.gov.bcb.pix";
    	
    	int valorLen = valorStr.length();    	
    	int credorLen = credor.length();    	
    	int guiLen = gui.length();    	    	
    	int chaveLen = chave.length();
    	int cidadeLen = cidade.length();

    	String valorPagtoLenStr = ( valorLen < 10 ? "0" : "" ) + valorLen;
    	String credorLenStr = ( credorLen < 10 ? "0" : "" ) + credorLen;
    	String guiLenStr = ( guiLen < 10 ? "0" : "" ) + guiLen;
    	String chaveLenStr = ( chaveLen < 10 ? "0" : "" ) + chaveLen;
    	String cidadeLenStr = ( cidadeLen < 10 ? "0" : "" ) + cidadeLen;
    	
    	StringBuilder qrcodePayloadSub1 = new StringBuilder();
    	qrcodePayloadSub1.append( "00"+guiLenStr+gui );
    	qrcodePayloadSub1.append( "01"+chaveLenStr+chave );    	
    	    	    
    	String qrcodePayloadSub1Str = qrcodePayloadSub1.toString();
    	int sub1Len = qrcodePayloadSub1Str.length();
    	String sub1LenStr = ( sub1Len < 10 ? "0" : "" ) + sub1Len;
    	
    	StringBuilder qrcodePayloadSub2 = new StringBuilder();
    	qrcodePayloadSub2.append( "0503***" );
    	
    	String qrcodePayloadSub2Str = qrcodePayloadSub2.toString();
    	int sub2Len = qrcodePayloadSub2Str.length();
    	String sub2LenStr = ( sub2Len < 10 ? "0" : "" ) + sub2Len;
    	    	
    	StringBuilder qrcodePayload = new StringBuilder(); 
    	qrcodePayload.append( "000201" );
    	qrcodePayload.append( "26"+sub1LenStr+qrcodePayloadSub1Str );
    	qrcodePayload.append( "52040000" );
    	qrcodePayload.append( "5303986" );
    	qrcodePayload.append( "54"+valorPagtoLenStr+valorStr );
    	qrcodePayload.append( "5802BR" );
    	qrcodePayload.append( "59"+credorLenStr+credor );
    	qrcodePayload.append( "60"+cidadeLenStr+cidade );    	
    	qrcodePayload.append( "62"+sub2LenStr+qrcodePayloadSub2Str );
    	qrcodePayload.append( "6304" );
    	
    	String payload = qrcodePayload.toString();
    	    	
    	int crc = this.computeCRC( payload.getBytes( "UTF-8" ) );
    	
    	return payload + Integer.toHexString( crc ).toUpperCase();		
	}
	
	public int computeCRC(byte[] data) {        
        int crc = 0xFFFF;
        int polynomial = 0x1021;

        for (byte b : data) {
            int currentByte = b & 0xFF;
            crc ^= (currentByte << 8);

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else {
                    crc <<= 1;
                }
                crc &= 0xFFFF;
            }
        }
        return crc & 0xFFFF;
    }
	
	public BufferedImage qrcodeToImage( String qrcodeText, int scale, int border ) {
		QrCode qrcode = QrCode.encodeText( qrcodeText, QrCode.Ecc.MEDIUM );
		
		BufferedImage image = new BufferedImage( 
				(qrcode.size + (2*border)) * scale,
				(qrcode.size + (2*border)) * scale,
				BufferedImage.TYPE_INT_ARGB );
		
		for( int y = 0; y < image.getHeight(); y++ ) {
			for( int x = 0; x < image.getWidth(); x++ ) {
				boolean isColor = qrcode.getModule( (x / scale) - border, (y / scale) - border );
				image.setRGB( x, y, isColor ? 0xFF000000 : 0x00FFFFFF );
			}
		}
		
		return image;
	}
	
}
