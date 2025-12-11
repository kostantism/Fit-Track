package gr.hua.dit.fittrack.core.port.impl.dto;

import java.util.List;

public record ForecastEntry(
        long dt,
        WeatherData main,
        List<WeatherDescription> weather
) {
    public java.time.LocalDateTime dtAsLocalDateTime() {
        return java.time.LocalDateTime.ofEpochSecond(dt, 0, java.time.ZoneOffset.UTC);
    }
}
