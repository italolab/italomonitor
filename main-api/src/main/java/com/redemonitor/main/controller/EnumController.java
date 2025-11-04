package com.redemonitor.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.components.util.EnumUtil;
import com.redemonitor.main.dto.response.EnumOptionResponse;
import com.redemonitor.main.enums.UsuarioPerfil;

@RestController
@RequestMapping("/api/v1/enums")
public class EnumController {
	
	@Autowired
	private EnumUtil enumUtil;
	
	@GetMapping("/usuario-perfis")
	public ResponseEntity<List<EnumOptionResponse>> getUsuarioPerfis() {
		List<EnumOptionResponse> options = enumUtil.optionsResponse( UsuarioPerfil.values() );
		return ResponseEntity.ok( options );
	}

}
