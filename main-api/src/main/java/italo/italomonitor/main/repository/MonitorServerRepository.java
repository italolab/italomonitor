package italo.italomonitor.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.italomonitor.main.model.MonitorServer;

public interface MonitorServerRepository extends JpaRepository<MonitorServer, Long> {

	@Query( "select ms from MonitorServer ms where lower(ms.host) like lower(?1)" )
    List<MonitorServer> filter( String hostPart );
	
	public Optional<MonitorServer> findByHost( String host );
	
}
