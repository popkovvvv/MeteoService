package com.meteo.sber.controller;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.model.request.MessageRequest;
import com.meteo.sber.service.WeatherConfigService;
import com.meteo.sber.service.WeatherScheduler;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class WeatherController {

    WeatherService weatherService;

    WeatherConfigService weatherConfigService;

    WeatherScheduler weatherScheduler;

    @Autowired
    public WeatherController( WeatherService weatherService,
                              WeatherConfigService weatherConfigService,
                              WeatherScheduler weatherScheduler) {
        this.weatherService = weatherService;
        this.weatherConfigService = weatherConfigService;
        this.weatherScheduler = weatherScheduler;
    }

    @GetMapping("/weather/{city}")
    public WeatherEntity getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @DeleteMapping("/weather/{city}")
    public MessageRequest deleteWeather( @PathVariable String city){
        return weatherService.deleteWeather(city);
    }

    @PostMapping("/weather/update")
    public void setUpdate(@RequestParam String city, @RequestParam String bool){
        boolean update = Boolean.parseBoolean(bool);
        weatherConfigService.updateWeather(city,update);
    }

    @PostMapping("/weather/time")
    public MessageRequest setTimeUp(@RequestParam String seconds){
        int update = Integer.parseInt(seconds);
        return weatherScheduler.changeTimeUp(update);
    }

    @GetMapping("/weather/all")
    public List<WeatherEntity> getWeatherList(){
       return weatherService.getWeatherList();
    }

}
