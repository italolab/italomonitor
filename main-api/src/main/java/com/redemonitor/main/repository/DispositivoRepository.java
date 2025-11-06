package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    @Query( "select d from Dispositivo d where d.empresa.id=?1" )
    List<Dispositivo> list( Long empresaId );
    
    @Query( "select d.id from Dispositivo d where d.sendoMonitorado=?1") 
    List<Long> findIDsBySendoMonitorado( boolean sendoMonitorado );
    
    @Query( "select d.id from Dispositivo d where d.empresa.id=?1")
    List<Long> findIDsByEmpresaId( Long empresaId );
    
    @Query( "select count(*) from Dispositivo d where d.empresa.id=?1")
    int countDispositivosByEmpresa( Long empresaId );
    
    @Query( "select d.empresa.id from Dispositivo d where d.id=?1")
    Optional<Long> getEmpresaId( Long dispositivoId );
    
    Optional<Dispositivo> findByNome( String nome );

}
