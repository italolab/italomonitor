package com.italomonitor.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.italomonitor.main.model.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    @Query( "select d from Dispositivo d where d.empresa.id=?1" )
    List<Dispositivo> list( Long empresaId );
    
    @Query( "select d.id from Dispositivo d where d.monitoradoPorAgente=false") 
    List<Long> findAllIDsNoMonitByAgente();
    
    @Query( "select d.id from Dispositivo d where d.empresa.id=?1 and d.monitoradoPorAgente=false")
    List<Long> findIDsByEmpresaIdNoMonitByAgente( Long empresaId );
    
    @Query( "select count(*) from Dispositivo d where d.empresa.id=?1")
    int countByEmpresa( Long empresaId );
    
    @Query( "select count(*) from Dispositivo d where d.empresa.id=?1 and d.sendoMonitorado=?2" )
    int countByEmpresaBySendoMonitorado( Long empresaId, boolean sendoMonitorado );
        
    @Query( "select d.empresa.id from Dispositivo d where d.id=?1")
    Optional<Long> getEmpresaId( Long dispositivoId );
    
    @Query( "select d.nome, d.status from Dispositivo d where d.empresa.id=?1")
    List<Object[]> getNomesAndStatusesByEmpresaId( Long empresaId );
    
    @Query( "select d from Dispositivo d where d.empresa.id=?2 and d.nome=?1" )
    Optional<Dispositivo> findByNomeAndEmpresa( String nome, Long empresaId );
    
    @Transactional
    @Modifying
    @Query( "update Dispositivo d set d.sendoMonitorado=false" )
    void updateAllToNaoSendoMonitorado();
    
}
