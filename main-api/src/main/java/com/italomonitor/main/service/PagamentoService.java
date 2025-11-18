package com.italomonitor.main.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.italomonitor.main.components.QrCodeCreator;
import com.italomonitor.main.components.util.DateUtil;
import com.italomonitor.main.components.util.ImageUtil;
import com.italomonitor.main.dto.response.PagamentoPixQrCodeResponse;
import com.italomonitor.main.exception.BusinessException;
import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.model.Config;
import com.italomonitor.main.model.Empresa;
import com.italomonitor.main.repository.ConfigRepository;
import com.italomonitor.main.repository.EmpresaRepository;

@Service
public class PagamentoService {

	@Value("${pix.qrcode.chave}")
	private String chave;
	
	@Value("${pix.qrcode.recebedor}")
	private String recebedor;
	
	@Value("${pix.qrcode.cidade}")
	private String cidade;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private QrCodeCreator qrCodeCreator;	
	
	@Autowired
	private ImageUtil imageUtil;
	
	@Autowired
	private DateUtil dateUtil;
	
	public PagamentoPixQrCodeResponse geraPagamentoPixQrCode( Long empresaId ) {
		Optional<Empresa> empresaOp = empresaRepository.findById( empresaId );
		if ( empresaOp.isEmpty() )
			throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
		
		Empresa empresa = empresaOp.get();
		
		if ( empresa.getPagoAte() == null )
			throw new BusinessException( Errors.STILL_A_TEMPORARY_EMPRESA );
		
		Config config = configRepository.findFirstByOrderByIdAsc();
		
		LocalDate pagoAte = dateUtil.dateToLocalDate( empresa.getPagoAte() );
		LocalDate dataAtual = LocalDate.now();
		
		int diaPagto = empresa.getDiaPagto();
		int diaAtual = dataAtual.getDayOfMonth();
		
		int mes = pagoAte.getMonthValue();
		int mesAtual = dataAtual.getMonthValue();
		
		if ( diaAtual < diaPagto )
			mesAtual--;
				
    	double valorPagto = config.getValorPagto() * ( mesAtual - mes );
    	
		String qrcodeImageBase64 = null;
		String qrcodeText = null;

		try {
    		qrcodeText = qrCodeCreator.genQrCodeText( recebedor, chave, cidade, valorPagto );     	
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
	
	public String regularizaDivida( Long empresaId ) { 
		Optional<Empresa> empresaOp = empresaRepository.findById( empresaId );
		if ( empresaOp.isEmpty() )
			throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
		
		Empresa empresa = empresaOp.get();
		
		if ( empresa.getPagoAte() == null )
			throw new BusinessException( Errors.STILL_A_TEMPORARY_EMPRESA );
		
		LocalDate dataAtual = LocalDate.now();
		
		int diaPagto = empresa.getDiaPagto();
		int diaAtual = dataAtual.getDayOfMonth();
				
		LocalDate pagoAte;
		if ( diaAtual < diaPagto ) {
			pagoAte = dataAtual.plusDays( diaPagto - diaAtual ).minusMonths( 1 );			
		} else {
			pagoAte = dataAtual.minusDays( diaAtual - diaPagto );
		}
		
		Date pagoAteDt = dateUtil.localDateToDate( pagoAte );
		empresa.setPagoAte( pagoAteDt ); 
		
		empresaRepository.save( empresa );
		
		String pagoAteStr = dateUtil.dateFormat( pagoAte );		
		
		return "Dívida regularizada. Pago até: " + pagoAteStr; 
	}
	
}
