package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    @Query( "select d from Dispositivo d where d.empresa.id=?1 and "
    		+ "d.host like ?2 and "
    		+ "lower(d.nome) like lower(?3) and "
    		+ "lower(d.localizacao) like lower(?4)" )
    List<Dispositivo> filter( Long empresaId, String hostPart, String nomePart, String localPart );
    
    @Query( "select d.id from Dispositivo d where d.empresa.id=?1")
    List<Long> findIDsByEmpresaId( Long empresaId );
    
    @Query( "select count(*) from Dispositivo d where d.empresa.id=?1")
    int countDispositivosByEmpresa( Long empresaId );
    
    @Query( "select d.empresa.id from Dispositivo d where d.id=?1")
    Optional<Long> getEmpresaId( Long dispositivoId );
    
    Optional<Dispositivo> findByNome( String nome );

}
