package com.meteo.sber.repo;

import com.meteo.sber.model.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface WeatherRepo extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findByName(String name);
}
