package com.meteo.sber.controller;

import com.meteo.sber.model.WeatherEntity;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("weather") // Указываем префикс маршрута для всех экшенов
public class WeatherController {

    WeatherService weatherService;

    public WeatherController(@Autowired WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("{city}")
    public WeatherEntity getWeather(@PathVariable String city) throws IOException, InterruptedException {
        return weatherService.existDataAndMerge(city);
    }

}
