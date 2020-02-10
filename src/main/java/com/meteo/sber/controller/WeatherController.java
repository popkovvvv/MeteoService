package com.meteo.sber.controller;

import com.meteo.sber.model.pojo.Weather;
import com.meteo.sber.model.pojo.WeatherForecast;
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

    @GetMapping("/now/{country}/{city}")
    public Weather getWeather(@PathVariable String country,
                              @PathVariable String city){
        return weatherService.getWeather(country, city);
    }

    @RequestMapping("/weekly/{country}/{city}")
    public WeatherForecast getWeatherForecast(@PathVariable String country,
                                              @PathVariable String city) {
        return weatherService.getWeatherForecast(country, city);
    }

}
