package com.meteo.sber.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.meteo.sber.config.AppConfig;
import com.meteo.sber.model.request.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

@Service
public class WeatherScheduler implements SchedulingConfigurer {

    private WeatherConfigService weatherConfigService;

    private AppConfig appConfig;

    private volatile Integer timeToUp;

    private static final Logger logger = LoggerFactory.getLogger(WeatherScheduler.class);


    @Autowired
    public WeatherScheduler( WeatherConfigService weatherConfigService, AppConfig appConfig) {
        this.weatherConfigService = weatherConfigService;
        this.appConfig = appConfig;
        this.timeToUp = 20;
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(appConfig.poolScheduler());
        taskRegistrar.addTriggerTask(() -> {
            weatherConfigService.updateWeather();
        }, triggerContext -> {
            Calendar nextExecutionTime = new GregorianCalendar();
            Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
            nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
            nextExecutionTime.add(Calendar.SECOND, timeToUp);
            logger.info("Time to up {}", timeToUp);
            return nextExecutionTime.getTime();
        });
    }

    public MessageRequest changeTimeUp(Integer seconds) {
        this.timeToUp = seconds;
        return new MessageRequest("OK", HttpStatus.OK.value());
    }

}