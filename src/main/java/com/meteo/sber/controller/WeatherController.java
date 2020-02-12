package com.meteo.sber.controller;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class WeatherController {

    WeatherService weatherService;

    public WeatherController(@Autowired WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/get/{city}")
    public WeatherEntity getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @PostMapping("/weather/update")
    public void setUpdate(@RequestParam String city, @RequestParam String bool){
        boolean update = Boolean.parseBoolean(bool);
        weatherService.setUpdateCity(city,update);
    }

}
