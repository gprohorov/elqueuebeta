package com.med.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Settings;
import com.med.repository.SettingsRepository;

@Service
public class SettingsService {

    @Autowired
    SettingsRepository repository;

    public Settings get() {
    	return repository.findAll().stream().findFirst().orElse(new Settings());
    }

    public Boolean update(Settings model) {
    	return (repository.save(model) != null);
    }
}