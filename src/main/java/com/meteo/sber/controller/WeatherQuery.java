package com.meteo.sber.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherQuery implements GraphQLQueryResolver {

    private WeatherService weatherService;

    @Autowired
    public WeatherQuery(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public List<WeatherEntity> getWeathers() {
        return weatherService.getWeatherList();
    }

    public WeatherEntity getWeather(String name) {
        return weatherService.getWeather(name);
    }
}
