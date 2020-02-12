package com.meteo.sber.controller;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.WeatherConfigService;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class WeatherController {

    WeatherService weatherService;

    WeatherConfigService weatherConfigService;

    @Autowired
    public WeatherController( WeatherService weatherService, WeatherConfigService weatherConfigService ) {
        this.weatherService = weatherService;
        this.weatherConfigService = weatherConfigService;
    }

    @GetMapping("/weather/{city}")
    public WeatherEntity getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @DeleteMapping("/weather/{city}")
    public WeatherEntity deleteWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @PostMapping("/weather/update")
    public void setUpdate(@RequestParam String city, @RequestParam String bool){
        boolean update = Boolean.parseBoolean(bool);
        weatherConfigService.updateWeather(city,update);
    }

    @GetMapping("/weather/all")
    public List<WeatherEntity> getWeatherList(){
       return weatherService.getWeatherList();
    }

}
