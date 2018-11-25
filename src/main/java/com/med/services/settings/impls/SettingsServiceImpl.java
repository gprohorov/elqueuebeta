package com.med.services.settings.impls;

import com.med.model.Settings;
import com.med.repository.settings.SettingsRepository;
import com.med.services.settings.interfaces.iSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by george on 22.11.18.
 */
@Service
public class SettingsServiceImpl implements iSettingsService {

    @Autowired
    SettingsRepository repository;

    @Override
    public Settings get() {
    	return repository.findAll().stream().findFirst().orElse(new Settings());
    }

    @Override
    public Boolean update(Settings model) {
    	return repository.save(model) == null ? false : true;
    }
}
