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
	private int weatherId;

	@Column
	private String country;

	@Column
	private int sunrise;

	@Column
	private int sunset;

	@Column
	private Date updatedAt;

	@Column
	private boolean update;

	@Column(name = "cityName")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp( Instant timestamp ) {
		this.timestamp = timestamp;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature( double temperature ) {
		this.temperature = temperature;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed( double windSpeed ) {
		this.windSpeed = windSpeed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage( String shortMessage ) {
		this.shortMessage = shortMessage;
	}

	public int getWeatherId() {
		return weatherId;
	}

	public void setWeatherId( int weatherId ) {
		this.weatherId = weatherId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry( String country ) {
		this.country = country;
	}

	public int getSunrise() {
		return sunrise;
	}

	public void setSunrise( int sunrise ) {
		this.sunrise = sunrise;
	}

	public int getSunset() {
		return sunset;
	}

	public void setSunset( int sunset ) {
		this.sunset = sunset;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt( Date updatedAt ) {
		this.updatedAt = updatedAt;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate( boolean update ) {
		this.update = update;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public boolean equals( Object o ) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		WeatherEntity that = (WeatherEntity) o;

		if (Double.compare(that.temperature, temperature) != 0) return false;
		if (Double.compare(that.windSpeed, windSpeed) != 0) return false;
		if (weatherId != that.weatherId) return false;
		if (sunrise != that.sunrise) return false;
		if (sunset != that.sunset) return false;
		if (update != that.update) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
		if (message != null ? !message.equals(that.message) : that.message != null) return false;
		if (shortMessage != null ? !shortMessage.equals(that.shortMessage) : that.shortMessage != null) return false;
		if (country != null ? !country.equals(that.country) : that.country != null) return false;
		if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id != null ? id.hashCode() : 0;
		result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
		temp = Double.doubleToLongBits(temperature);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(windSpeed);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + (shortMessage != null ? shortMessage.hashCode() : 0);
		result = 31 * result + weatherId;
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + sunrise;
		result = 31 * result + sunset;
		result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
		result = 31 * result + (update ? 1 : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
