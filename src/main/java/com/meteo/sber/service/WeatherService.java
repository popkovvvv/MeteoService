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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private static final String USER_AGENT = "Mozilla/5.0";

    @Value("${meteo.key}")
    private String apiKey;

    private WeatherRepo weatherRepo;

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private RestTemplate restTemplate;

    private ModelMapper modelMapper;

    public WeatherService(@Autowired WeatherRepo weatherRepo, @Autowired RestTemplateBuilder restTemplateBuilder,
                          @Autowired ModelMapper modelMapper) {
        this.weatherRepo = weatherRepo;
        this.restTemplate = restTemplateBuilder.build();
        this.modelMapper = modelMapper;
    }

    @Cacheable("weather")
    public WeatherEntity getWeather(String city) {
        logger.info("Requesting current weather for {}", city);
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        WeatherPojo weatherRequest = invoke(url, WeatherPojo.class);
        WeatherEntity weatherEntity = convertToEntity(weatherRequest);
        return insertInData(weatherEntity);
    }

    public WeatherEntity insertInData(WeatherEntity weatherEntity) {
        Optional<WeatherEntity> weather = weatherRepo.findByName(weatherEntity.getName());
        if (weather.isPresent()){
            return weather.get();
        }
        weatherRepo.save(weatherEntity);
        return weatherEntity;
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }

    private WeatherEntity convertToEntity(WeatherPojo weatherPojo){
        return modelMapper.map(weatherPojo, WeatherEntity.class);
    }

    public void setUpdateCity(String name, boolean bool){
       Optional<WeatherEntity> weather =  weatherRepo.findByName(name);
       if (weather.isPresent()){
           WeatherEntity weatherEntity = weather.get();
           weatherEntity.setUpdate(bool);
           weatherRepo.save(weatherEntity);

       }
    }

    public void updateWeathers(WeatherEntity weatherEntity) {
        this.getWeather(weatherEntity.getName());
        logger.info("Update weather in city " + weatherEntity.getName());
    }

    public List<WeatherEntity> getWeatherList() {
        List<WeatherEntity> weatherEntities = new ArrayList<>();
        weatherRepo.findAll().forEach(weatherEntities::add);
        return weatherEntities;
    }
}
