package gr.hua.dit.fittrack.core.port.impl.dto;

public record WeatherInfo(
        String city,
        double temperatureCelsius,
        double humidityPercent,
        String conditions
) {}
