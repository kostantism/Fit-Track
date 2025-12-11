package gr.hua.dit.fittrack.core.port.impl.dto;

import java.util.List;

public record WeatherApiResponse(
        WeatherData weatherData,
        List<WeatherDescription> weather,
        String name
) {}
