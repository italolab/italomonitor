package com.redemonitor.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.dto.request.SaveConfigRequest;
import com.redemonitor.main.dto.response.ConfigResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.ConfigMapper;
import com.redemonitor.main.model.Config;
import com.redemonitor.main.repository.ConfigRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    public void updateConfig( SaveConfigRequest request ) {
        request.validate();

        Config config = configRepository.findFirstByOrderByIdAsc();

        configMapper.load( config, request );
        configRepository.save( config );
    }

    public ConfigResponse getConfig() {
        Config config = configRepository.findFirstByOrderByIdAsc();
        return configMapper.map( config );
    }

}
