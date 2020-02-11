package com.meteo.sber.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table
public class WeatherEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Instant timestamp;

	@Column
	private double temperature;

	@Column
	private double windSpeed;

	@Column
	private String message;

	@Column
	private String shortMessage;

	@Column
	private Integer weatherId;

	@Column
	private String country;

	@Column
	private Integer sunrise;

	@Column
	private Integer sunset;

	@Column
	private Date schedule;

	@Column(name = "cityName")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public Date getSchedule() {
		return schedule;
	}

	public void setSchedule( Date schedule ) {
		this.schedule = schedule;
	}

	public void setTimestamp( Instant timestamp) {
		this.timestamp = timestamp;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
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

	public Integer getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
