package gr.hua.dit.fittrack.core.port;

import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;

/**
 * Port for accessing external weather information services.
 */
public interface WeatherPort {
    WeatherInfo getCurrentWeather(final String cityName);
}
