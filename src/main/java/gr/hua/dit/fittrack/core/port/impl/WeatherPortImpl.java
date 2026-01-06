package gr.hua.dit.fittrack.core.port.impl;

import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherApiResponse;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WeatherPortImpl implements WeatherPort {

    private final RestTemplate restTemplate;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherPortImpl(RestTemplate restTemplate) {
        if (restTemplate == null) throw new NullPointerException();
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherInfo getForecast(String cityName, LocalDateTime dateTime) {

        if (cityName == null || cityName.isBlank()) {
            throw new IllegalArgumentException("City name is required");
        }
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime is required");
        }

        // OpenWeather Forecast API (3-hour intervals)
        String url = baseUrl
                + "/forecast?q=" + cityName
                + "&units=metric"
                + "&appid=" + apiKey;

        ResponseEntity<WeatherApiResponse> response =
                restTemplate.getForEntity(url, WeatherApiResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Weather API error: " + response.getStatusCode());
        }

        WeatherApiResponse body = response.getBody();
        if (body == null || body.list() == null || body.list().isEmpty()) {
            throw new RuntimeException("Empty weather response");
        }

        // Βρες το forecast πιο κοντά στο ζητούμενο dateTime
        long targetEpoch = dateTime.toEpochSecond(ZoneOffset.UTC);

        var bestMatch = body.list().stream()
                .min((a, b) -> {
                    long diffA = Math.abs(a.dt() - targetEpoch);
                    long diffB = Math.abs(b.dt() - targetEpoch);
                    return Long.compare(diffA, diffB);
                })
                .orElseThrow();

        // Μετατροπή σε DTO
        return new WeatherInfo(
                body.city().name(),
                bestMatch.main().temp(),
                bestMatch.main().humidity(),
                bestMatch.weather().get(0).description()
        );
    }
}
