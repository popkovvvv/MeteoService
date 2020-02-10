package com.meteo.sber.service;


import com.meteo.sber.model.Weather;
import com.meteo.sber.model.pojo.WeatherForecast;
import com.meteo.sber.repo.WeatherRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;


@Service
public class WeatherService {

    private static final String USER_AGENT = "Mozilla/5.0";

    @Value("${meteo.key}")
    private static String apiKey;

    WeatherRepo weatherRepo;

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}";

    private static final String FORECAST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?q={city},{country}&APPID={key}";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public WeatherService(@Autowired WeatherRepo weatherRepo, RestTemplate restTemplate) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplate;
    }

    private final RestTemplate restTemplate;

    @Cacheable("weather")
    public Weather getWeather(String country, String city) {
        logger.info("Requesting current weather for {}/{}", country, city);
        URI url = new UriTemplate(WEATHER_URL).expand(city, country, apiKey);
        return invoke(url, Weather.class);
    }

    @Cacheable("forecast")
    public WeatherForecast getWeatherForecast(String country, String city) {
        logger.info("Requesting weather forecast for {}/{}", country, city);
        URI url = new UriTemplate(FORECAST_URL).expand(city, country, apiKey);
        return invoke(url, WeatherForecast.class);
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }

}
