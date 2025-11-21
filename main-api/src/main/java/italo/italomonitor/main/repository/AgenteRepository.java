package italo.italomonitor.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.italomonitor.main.model.Agente;

public interface AgenteRepository extends JpaRepository<Agente, Long> {

	@Query( "select a from Agente a where lower(a.nome) like lower(?1) and a.empresa.id=?2" )
    List<Agente> filter( String nomePart, Long empresaId );
	
	@Query( "select a from Agente a where lower(a.nome)=lower(?1) and a.empresa.id=?2" )
	Optional<Agente> findByNomeAndEmpresa( String nome, Long empresaId );
	
	@Query( "select a.empresa.id from Agente a where a.id=?1" )
	Optional<Long> getEmpresaId( Long agenteId );
	
	Optional<Agente> findByChave( String chave );

}
