package com.med.repository.settings;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Settings;

@Repository
public interface SettingsRepository extends MongoRepository<Settings, String> {}