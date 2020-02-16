package com.meteo.sber.service;


import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.model.pojo.WeatherPojo;
import com.meteo.sber.repo.WeatherRepo;
import org.modelmapper.ModelMapper;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${meteo.key}")
    private String apiKey;

    private WeatherRepo weatherRepo;

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private RestTemplate restTemplate;

    private ModelMapper modelMapper;

    @Autowired
    public WeatherService(WeatherRepo weatherRepo,  RestTemplateBuilder restTemplateBuilder, ModelMapper modelMapper) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplateBuilder.build();
        this.modelMapper = modelMapper;
    }

    @Cacheable("weather")
    public WeatherEntity getWeather(String city) {
        WeatherEntity weatherEntity = weatherRepo.findByName(city).orElseGet(() -> request(city));
        return getWeatherOrSave(weatherEntity);
    }

    public WeatherEntity request(String city) {
        logger.info("Requesting current weather for {}", city);
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        WeatherPojo weatherRequest = invoke(url, WeatherPojo.class);
        return convertToEntity(weatherRequest);
    }

    public void delete(String name) {
       weatherRepo.findByName(name).ifPresent(weatherRepo::delete);
    }

    public WeatherEntity save(WeatherEntity weatherEntity) {
        weatherRepo.save(weatherEntity);
        return weatherEntity;
    }
    private WeatherEntity getWeatherOrSave(WeatherEntity weatherEntity) {
        return weatherRepo.findByName(weatherEntity.getName()).orElseGet(() -> save(weatherEntity));
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }

    public WeatherEntity convertToEntity(WeatherPojo weatherPojo){
        return modelMapper.map(weatherPojo, WeatherEntity.class);
    }

    public void updateWeathers(WeatherEntity weatherEntity) {
        WeatherEntity weather = request(weatherEntity.getName());
        save(weather);
        logger.info("Update weather in city " + weatherEntity.getName());
    }

    public List<WeatherEntity> getWeatherList() {
        return new ArrayList<>(weatherRepo.findAll());
    }
}
