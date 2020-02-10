package com.meteo.sber.repo;

import com.meteo.sber.model.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WeatherRepo extends CrudRepository<WeatherEntity, Long> {

    Optional<WeatherEntity> findByCityName(String name);
}
