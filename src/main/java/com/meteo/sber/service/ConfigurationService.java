package com.meteo.sber.service;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.repo.WeatherRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigurationService {

    private static final Logger LOGGER  = LoggerFactory.getLogger(ConfigurationService.class);

    WeatherRepo weatherRepo;

    WeatherService weatherService;

    private Map<String, WeatherEntity> configurationList;

    private List<String> weatherConf;

    @Autowired
    public ConfigurationService(WeatherRepo weatherRepo, WeatherService weatherService) {
        this.weatherRepo = weatherRepo;
        this.configurationList = new ConcurrentHashMap<>();
        this.weatherConf = new ArrayList<>();
        this.weatherService = weatherService;
    }

    /**
     * Loads configuration parameters from Database
     */
    @PostConstruct
    public void loadConfigurations() {
        LOGGER.debug("Scheduled Event: Configuration table loaded/updated from database");
        StringBuilder sb = new StringBuilder();
        sb.append("Configuration Parameters:");
        List<WeatherEntity> configs = (List<WeatherEntity>) weatherRepo.findAll();
        for (WeatherEntity weatherEntity : configs) {
            if (weatherEntity.getUpdate()){
                if (!configurationList.containsKey(weatherEntity.getName())) {
                    sb.append("\n")
                            .append(weatherEntity.getSchedule())
                            .append(":")
                            .append(weatherEntity.getUpdate());
                    this.configurationList.put(weatherEntity.getName(), weatherEntity);
                }
                weatherService.updateWeathers(weatherEntity);
            }
        }
        LOGGER.debug(sb.toString());

        checkWeatherConfigurations();
    }

    public WeatherEntity getConfiguration(String key) {
        return configurationList.get(key);
    }

    /**
     * Checks if the mandatory parameters are exists in Database
     */
    public void checkWeatherConfigurations() {
        for (String conf : weatherConf) {
            boolean exists = false;
            for (Map.Entry<String, WeatherEntity> pair : configurationList.entrySet()) {
                if (pair.getKey().equalsIgnoreCase(conf) && !pair.getValue().getName().isEmpty()) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                String errorLog = String.format("A mandatory Configuration parameter is not found in DB: %s", conf);
                LOGGER.error(errorLog);
            }
        }

    }

}