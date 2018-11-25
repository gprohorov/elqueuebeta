package com.med.services.settings.interfaces;

import com.med.model.Settings;
/**
 * Created by george on 22.11.18.
 */
public interface iSettingsService {
	Settings get();
    Boolean update(Settings model);
}
