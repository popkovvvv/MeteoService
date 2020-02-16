package com.meteo.sber.controller;

import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.schedule.WeatherScheduleService;
import com.meteo.sber.service.schedule.WeatherScheduler;
import com.meteo.sber.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value="Weather Controller")
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

    @ApiOperation(value = "Получение погоды по названию города, если нет в базе будет запрос", response = WeatherEntity.class)
    @GetMapping("/weather/{city}")
    public WeatherEntity getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @ApiOperation(value = "Удаление города из системы по названию")
    @DeleteMapping("/weather/{city}")
    public void deleteWeather( @PathVariable String city){
        weatherService.delete(city);
    }

    @ApiOperation(value = "Поставить город на обновление")
    @PutMapping("/weather/update")
    public void setUpdate(@RequestParam String city, @RequestParam Boolean bool){
        weatherScheduleService.updateWeather(city, bool);
    }


    @ApiOperation(value = "Выставление таймеру интервал в секундах", response = com.meteo.sber.model.request.Message.class)
    @PutMapping("/weather/time")
    public com.meteo.sber.model.request.Message setTimeUp( @RequestParam Integer seconds){
        return weatherScheduler.changeTimeUp(seconds);
    }

    @ApiOperation(value = "Получение списка погоды по количеству", response = WeatherEntity.class)
    @GetMapping("/weathers/{count}")
    public List<WeatherEntity> getWeatherList(@PathVariable int count){
       return weatherService.getWeatherList().stream().limit(count).collect(Collectors.toList());
    }

}
