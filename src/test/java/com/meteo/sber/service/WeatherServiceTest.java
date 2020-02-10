package com.meteo.sber.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.WeatherService;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriTemplate;


@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
@TestPropertySource(properties = "app.weather.api.key=test-ABC")
public class WeatherServiceTest {

	private static final String WEATHER_URL =
			"http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}";

	@Value("${meteo.key}")
	private String apiKey;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void getWeather() {
		this.server.expect(
				requestTo(new UriTemplate(WEATHER_URL).expand("Moscow", apiKey)))
				.andRespond(withSuccess(
						new ClassPathResource("weather.json", getClass()),
						MediaType.APPLICATION_JSON));
		WeatherEntity forecast = this.weatherService.getWeather("Moscow");
		assertThat(forecast.getName()).isEqualTo("Moscow");
		assertThat(forecast.getTemperature()).isEqualTo(286.72, Offset.offset(0.1));
		assertThat(forecast.getWeatherId()).isEqualTo(800);
	}


}
