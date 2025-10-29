package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redemonitor.main.model.Config;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findFirstByOrderByIdAsc();

}
