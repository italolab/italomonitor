package com.redemonitor.service;

import com.redemonitor.dto.request.SaveConfigRequest;
import com.redemonitor.dto.response.ConfigResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.ConfigMapper;
import com.redemonitor.model.Config;
import com.redemonitor.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
