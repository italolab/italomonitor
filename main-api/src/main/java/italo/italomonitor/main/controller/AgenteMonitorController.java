package italo.italomonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import italo.italomonitor.main.apidoc.agente.monitor.GetConfigAgMonitDoc;
import italo.italomonitor.main.apidoc.agente.monitor.GetDispositivoAgMonitDoc;
import italo.italomonitor.main.apidoc.agente.monitor.PostDispositivoStateAgMonitDoc;
import italo.italomonitor.main.apidoc.agente.monitor.PostEventoAgMonitDoc;
import italo.italomonitor.main.dto.integration.DispMonitorConfig;
import italo.italomonitor.main.dto.integration.DispMonitorDispositivo;
import italo.italomonitor.main.dto.integration.DispMonitorDispositivoState;
import italo.italomonitor.main.dto.integration.DispMonitorEvento;
import italo.italomonitor.main.service.AgenteMonitorService;

@RestController
@RequestMapping("/api/v1/monitoramento/agente")
public class AgenteMonitorController {

	@Autowired
	private AgenteMonitorService agenteMonitorService;
	
	@PostDispositivoStateAgMonitDoc
	@PreAuthorize("hasAuthority('agente-monitor-all')")
	@PostMapping("/{chave}/dispositivo-state/post")
	public ResponseEntity<String> receivesDispositivoState( 
			@PathVariable String chave,
			@RequestBody DispMonitorDispositivoState dispState ) {
		agenteMonitorService.processaDispositivoState( chave, dispState );
		return ResponseEntity.ok( "Estado do dispositivo recebido com sucesso." ); 
	}
	
	@PostEventoAgMonitDoc
	@PreAuthorize("hasAuthority('agente-monitor-all')")
	@PostMapping("/{chave}/evento/post")
	public ResponseEntity<String> receivesEvento(
			@PathVariable String chave,
			@RequestBody DispMonitorEvento evento ) {
		agenteMonitorService.processaEvento( chave, evento );
		return ResponseEntity.ok( "Evento recebido com sucesso." );
	}
	
	@GetDispositivoAgMonitDoc
	@PreAuthorize("hasAuthority('agente-monitor-all')")
	@GetMapping("/{chave}/dispositivo/{dispositivoId}/get")
	public ResponseEntity<DispMonitorDispositivo> getDispositivo(
			@PathVariable String chave,
			@PathVariable Long dispositivoId ) {
		DispMonitorDispositivo resp = agenteMonitorService.getDispositivo( chave, dispositivoId );
		return ResponseEntity.ok( resp );
	}
	
	@GetConfigAgMonitDoc
	@PreAuthorize("hasAuthority('agente-monitor-all')")
	@GetMapping("/{chave}/config/get")
	public ResponseEntity<DispMonitorConfig> getConfig(	@PathVariable String chave ) {
		DispMonitorConfig resp = agenteMonitorService.getConfig( chave );
		return ResponseEntity.ok( resp );
	}
	
	
}
