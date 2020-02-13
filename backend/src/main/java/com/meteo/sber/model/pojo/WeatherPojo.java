package com.meteo.sber.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class WeatherPojo {

    private Instant timestamp;

    private double temperature;

    private double windSpeed;

    private String message;

    private String shortMessage;

    private Integer weatherId;

    private String country;

    private Integer sunrise;

    private Integer sunset;

    private String name;

    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("timestamp")
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @JsonSetter("dt")
    public void setTimestamp(long unixTime) {
        this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("main")
    public void setMain(Map<String, Object> main) {
        setTemperature(Double.parseDouble(main.get("temp").toString()));
    }

    @JsonProperty("wind")
    public void setWind(Map<String, Object> main) {
        setWindSpeed(Double.parseDouble(main.get("speed").toString()));
    }

    @JsonProperty("sys")
    public void setSys(Map<String, Object> main) {
        setCountry(main.get("country").toString());
        setSunrise(Integer.parseInt(main.get("sunrise").toString()));
        setSunset(Integer.parseInt(main.get("sunset").toString()));
    }

    public Integer getWeatherId() {
        return this.weatherId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        setMessage((String) weather.get("description"));
        setShortMessage((String) weather.get("main"));
    }

}
