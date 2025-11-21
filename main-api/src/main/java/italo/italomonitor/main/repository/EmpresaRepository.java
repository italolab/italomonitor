package italo.italomonitor.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import italo.italomonitor.main.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query( "select emp from Empresa emp where lower(emp.nome) like lower(?1)" )
    List<Empresa> filter( String nomePart );

    Optional<Empresa> findByNome(String nome );
    
    @Transactional
    @Modifying
    @Query( "update Empresa e set e.ultimaNotifEm=?2 where e.id=?1")
    void updateUltimaNotifEm( Long empresaId, Date ultimaNotifEm );

}
