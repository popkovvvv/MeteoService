package com.meteo.sber.config;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.repo.WeatherRepo;
import com.meteo.sber.service.WeatherService;
import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQlConfig {

   private final WeatherService weatherService;

    @Autowired
    public GraphQlConfig( WeatherService weatherService) {
        this.weatherService = weatherService;
    }



    @GraphQLQuery(name = "weather")
    public WeatherEntity weather(@GraphQLArgument(name = "name") String name) {
        return weatherService.findOne(name);
    }


}
