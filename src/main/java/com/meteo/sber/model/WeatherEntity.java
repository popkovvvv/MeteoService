package com.meteo.sber.model;

import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.List;

@Entity
@Table()
@EqualsAndHashCode
public class WeatherEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String cityName;

    @OneToMany
    private List<Weather> weathers;

    @Column
    private Double windSpeed;

    @Column
    private Integer pressure;

    @Column
    private Double temp;

    public WeatherEntity() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

}
