package com.meteo.sber.web;

import com.meteo.sber.controller.WeatherController;
import com.meteo.sber.model.entity.WeatherEntity;
import com.meteo.sber.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.time.Instant;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherApiControllerTest {

	@MockBean
	private WeatherService weatherService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void weather() throws Exception {
		WeatherEntity weather = new WeatherEntity();
		weather.setName("Moscow");
		setWeatherEntry(weather, 286.72, 800, "01d", Instant.ofEpochSecond(1234));
		given(this.weatherService.getWeather("Moscow")).willReturn(weather);
		this.mvc.perform(get("/api/v1/weather/Moscow"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Moscow")))
				.andExpect(jsonPath("$.temperature", is(286.72)))
				.andExpect(jsonPath("$.weatherId", is(800)))
				.andExpect(jsonPath("$.timestamp", is("1970-01-01T00:20:34Z")));
		verify(this.weatherService).getWeather("Moscow");
	}



	private static WeatherEntity createWeatherEntry(double temperature, int id, String icon,
			Instant timestamp) {
		WeatherEntity entry = new WeatherEntity();
		setWeatherEntry(entry, temperature, id, icon, timestamp);
		return entry;
	}

	private static void setWeatherEntry(WeatherEntity entry, double temperature, int id, String icon,
			Instant timestamp) {
		entry.setTemperature(temperature);
		entry.setWeatherId(id);
		entry.setTimestamp(timestamp.getEpochSecond());
	}

}
