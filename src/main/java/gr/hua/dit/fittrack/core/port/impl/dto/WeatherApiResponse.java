package gr.hua.dit.fittrack.core.port.impl.dto;

import java.util.List;

public record WeatherApiResponse(
        List<ForecastEntry> list,
        City city
) {}
