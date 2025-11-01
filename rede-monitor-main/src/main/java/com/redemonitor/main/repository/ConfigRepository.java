package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redemonitor.main.model.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {
	
    Config findFirstByOrderByIdAsc();

}
