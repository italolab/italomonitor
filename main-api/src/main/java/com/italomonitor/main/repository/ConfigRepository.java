package com.italomonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.italomonitor.main.model.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {
	
    Config findFirstByOrderByIdAsc();

}
