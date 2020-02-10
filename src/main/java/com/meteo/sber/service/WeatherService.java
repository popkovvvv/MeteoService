package com.meteo.sber.service;


import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.repo.WeatherRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
    private String apiKey;

    WeatherRepo weatherRepo;

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    RestTemplate restTemplate;

    public WeatherService(@Autowired WeatherRepo weatherRepo,
                          @Autowired RestTemplateBuilder restTemplateBuilder) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable("weather")
    public WeatherEntity getWeather(String city) {
        logger.info("Requesting current weather for {}", city);
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        WeatherEntity weatherRequest = invoke(url, WeatherEntity.class);
        weatherRepo.save(weatherRequest);
        return weatherRequest;
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }

}
