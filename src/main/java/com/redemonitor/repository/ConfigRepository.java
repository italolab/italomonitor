package com.redemonitor.repository;

import com.redemonitor.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findFirstByOrderByIdAsc();

}
