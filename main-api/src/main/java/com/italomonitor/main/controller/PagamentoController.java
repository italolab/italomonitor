package com.italomonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italomonitor.main.apidoc.pagamento.GetPagamentoPixQrCodeDoc;
import com.italomonitor.main.dto.response.PagamentoPixQrCodeResponse;
import com.italomonitor.main.service.AuthorizationService;
import com.italomonitor.main.service.PagamentoService;

@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@GetPagamentoPixQrCodeDoc
	@PreAuthorize("hasAuthority('pagamento-all')")
	@GetMapping("/empresa/{empresaId}/get/pix-qrcode")
	public ResponseEntity<PagamentoPixQrCodeResponse> getPagPixQrCode( 
			@PathVariable Long empresaId,
			@RequestHeader("Authorization") String authorizationHeader ) {
		
		authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
		
		PagamentoPixQrCodeResponse resp = pagamentoService.geraPagamentoPixQrCode( empresaId );
		return ResponseEntity.ok( resp );
	}
	
}
