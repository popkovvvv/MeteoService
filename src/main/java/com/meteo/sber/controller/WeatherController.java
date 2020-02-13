package com.meteo.sber.controller;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.model.request.MessageRequest;
import com.meteo.sber.service.schedule.WeatherScheduleService;
import com.meteo.sber.service.schedule.WeatherScheduler;
import com.meteo.sber.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class WeatherController {

    private final WeatherService weatherService;

    private final WeatherScheduleService weatherScheduleService;

    private final WeatherScheduler weatherScheduler;

    @Autowired
    public WeatherController(WeatherService weatherService,
                             WeatherScheduleService weatherScheduleService,
                             WeatherScheduler weatherScheduler) {
        this.weatherService = weatherService;
        this.weatherScheduleService = weatherScheduleService;
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
    public void setUpdate(@RequestParam String city, @RequestParam Boolean bool){
        weatherScheduleService.updateWeather(city, bool);
    }

    @PostMapping("/weather/time")
    public MessageRequest setTimeUp(@RequestParam Integer seconds){
        return weatherScheduler.changeTimeUp(seconds);
    }

    @GetMapping("/weathers/{count}")
    public List<WeatherEntity> getWeatherList(@PathVariable int count){
       return weatherService.getWeatherList().stream().limit(count).collect(Collectors.toList());
    }

}
