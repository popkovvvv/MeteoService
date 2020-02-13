package com.meteo.sber.service;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.repo.WeatherRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherConfigService {

    private static final Logger LOGGER  = LoggerFactory.getLogger(WeatherConfigService.class);

    WeatherRepo weatherRepo;

    WeatherService weatherService;

    private Map<String, WeatherEntity> weatherEntityMap;

    @Autowired
    public WeatherConfigService( WeatherRepo weatherRepo, WeatherService weatherService) {
        this.weatherRepo = weatherRepo;
        this.weatherEntityMap = new ConcurrentHashMap<>();
        this.weatherService = weatherService;
    }

    @PostConstruct
    public void loadWeather() {
        Iterable<WeatherEntity> configs = weatherRepo.findAll();
        for (WeatherEntity weatherEntity : configs) {
            if (weatherEntity.getUpdate()) {
                this.weatherEntityMap.put(weatherEntity.getName(), weatherEntity);
            }
        }
    }

    public void update() {
        LOGGER.debug("Scheduled Event: Configuration table loaded/updated from database");
        for (WeatherEntity weatherEntity : weatherEntityMap.values()) {
            if (weatherEntity.getUpdate()){
                weatherService.updateWeathers(weatherEntity);
            }
        }
    }

    public void updateWeather(String name, boolean bool) {
        Optional<WeatherEntity> weather =  weatherRepo.findByName(name);
        if (weather.isPresent()){
            WeatherEntity weatherEntity = weather.get();
            if (bool){
                weatherEntityMap.put(weatherEntity.getName(),weatherEntity);
            } else {
                weatherEntityMap.remove(weatherEntity.getName(), weatherEntity);
            }
            weatherEntity.setUpdate(bool);
            weatherRepo.save(weatherEntity);

        }
    }

}


