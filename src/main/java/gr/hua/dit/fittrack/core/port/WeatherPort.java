package gr.hua.dit.fittrack.core.port;

import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;

import java.time.LocalDateTime;

/**
 * Port for accessing external weather information services.
 */
public interface WeatherPort {
    WeatherInfo getForecast(String cityName, LocalDateTime dateTime);

}
