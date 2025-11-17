package com.italomonitor.main.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.italomonitor.main.components.QrCodeCreator;
import com.italomonitor.main.components.util.ImageUtil;
import com.italomonitor.main.dto.response.PagamentoPixQrCodeResponse;
import com.italomonitor.main.exception.BusinessException;
import com.italomonitor.main.exception.Errors;

@Service
public class PagamentoService {

	@Autowired
	private QrCodeCreator qrCodeCreator;
	
	@Autowired
	private ImageUtil imageUtil;
	
	public PagamentoPixQrCodeResponse geraPagamentoPixQrCode( Long empresaId ) {    	
    	double valorPagto = 0.01;
    	String credor = "ITALO_HERBERT_SIQUEIRA_GA";    	
    	String chave = "italoherbert@outlook.com";
    	String cidade = "BRASILIA";   	
    	
		String qrcodeImageBase64 = null;
		String qrcodeText = null;

		try {
    		qrcodeText = qrCodeCreator.genQrCodeText( credor, chave, cidade, valorPagto );     	
    		BufferedImage qrcodeImage = qrCodeCreator.qrcodeToImage( qrcodeText, 4, 10 );
    	    	
    		qrcodeImageBase64 = imageUtil.imageToBase64( qrcodeImage );
    	} catch ( IOException e ) {
    		Logger.getLogger( PagamentoService.class ).error( "Falha na geração do qrcode.\nErro="+e.getMessage() ); 
    		throw new BusinessException( Errors.QRCODE_GEN_ERROR );
    	}
    	
    	return PagamentoPixQrCodeResponse.builder()
    			.image( qrcodeImageBase64 )
    			.text( qrcodeText )
    			.build();
    }
	
}
