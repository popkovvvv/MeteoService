package com.meteo.sber.service;

import com.meteo.sber.config.TaskSchedulerConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ScheduledFuture;

@Service
public class WeatherScheduler implements SchedulingConfigurer {

    private static Logger LOGGER = LoggerFactory.getLogger(WeatherScheduler.class);

    ScheduledTaskRegistrar scheduledTaskRegistrar;

    ScheduledFuture future;

    WeatherService weatherService;

    TaskSchedulerConf taskSchedulerConf;

    public WeatherScheduler(@Autowired WeatherService weatherService, @Autowired TaskSchedulerConf taskSchedulerConf) {
        this.weatherService = weatherService;
        this.taskSchedulerConf = taskSchedulerConf;
    }

    @Override
    public void configureTasks( ScheduledTaskRegistrar taskRegistrar) {
        if (scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(taskSchedulerConf.poolScheduler());
        }

        future = taskRegistrar.getScheduler().schedule(this::schedule, t -> {
            Calendar nextExecutionTime = new GregorianCalendar();
            Date lastActualExecutionTime = t.lastActualExecutionTime();
            nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
            nextExecutionTime.add(Calendar.SECOND, 7);
            return nextExecutionTime.getTime();
        });
    }

    public void schedule() {
        weatherService.updateWeathers();
        LOGGER.info("scheduleFixed: Next execution time of this will always be 5 seconds");
    }

    /**
     * @param mayInterruptIfRunning {@code true} if the thread executing this task
     * should be interrupted; otherwise, in-progress tasks are allowed to complete
     */
    public void cancelTasks(boolean mayInterruptIfRunning) {
        LOGGER.info("Cancelling all tasks");
        future.cancel(mayInterruptIfRunning); // set to false if you want the running task to be completed first.
    }

    public void activateScheduler() {
        LOGGER.info("Re-Activating Scheduler");
        configureTasks(scheduledTaskRegistrar);
    }
}
