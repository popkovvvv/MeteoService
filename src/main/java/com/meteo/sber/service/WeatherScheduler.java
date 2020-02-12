package com.meteo.sber.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.meteo.sber.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

@Service
public class WeatherScheduler implements SchedulingConfigurer {

    private WeatherConfigService weatherConfigService;

    private AppConfig appConfig;

    @Autowired
    public WeatherScheduler( WeatherConfigService weatherConfigService, AppConfig appConfig) {
        this.weatherConfigService = weatherConfigService;
        this.appConfig = appConfig;
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
            nextExecutionTime.add(Calendar.SECOND, 15);
            return nextExecutionTime.getTime();
        });
    }

}