package com.meteo.sber.controller;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.model.request.MessageRequest;
import com.meteo.sber.service.WeatherConfigService;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherQuery implements GraphQLQueryResolver {

    private WeatherService weatherService;
    private WeatherConfigService weatherConfigService;

    @Autowired
    public WeatherQuery(WeatherService weatherService, WeatherConfigService weatherConfigService) {
        this.weatherService = weatherService;
        this.weatherConfigService = weatherConfigService;
    }

    public List<WeatherEntity> getWeathers(int count) {
        return weatherService.getWeatherList().stream().limit(count).collect(Collectors.toList());
    }

    public WeatherEntity getWeather(String name) {
        return weatherService.getWeather(name);
    }

    public MessageRequest deleteWeather(String city){
        return weatherService.deleteWeather(city);
    }

    public void updateWeather( String city, String bool){
        boolean update = Boolean.parseBoolean(bool);
        weatherConfigService.updateWeather(city,update);
    }
}
