package italo.italomonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import italo.italomonitor.main.model.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {
	
    Config findFirstByOrderByIdAsc();

}
