package gr.hua.dit.fittrack.core.port.impl;

import gr.hua.dit.fittrack.config.RestApiClientConfig;
import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherApiResponse;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WeatherPortImpl implements WeatherPort {

    private final RestTemplate restTemplate;

    public WeatherPortImpl(RestTemplate restTemplate) {
        if (restTemplate == null) throw new NullPointerException();
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherInfo getForecast(final String cityName, final LocalDateTime dateTime) {
        if (cityName == null) throw new NullPointerException();
        if (cityName.isBlank()) throw new IllegalArgumentException();
        if (dateTime == null) throw new NullPointerException();

        final String baseUrl = RestApiClientConfig.WEATHER_BASE_URL;
        final String apiKey = RestApiClientConfig.WEATHER_API_KEY;

        // Forecast API URL (3-hour intervals)
        final String url =
                baseUrl + "/data/2.5/forecast?q=" + cityName + "&units=metric&appid=" + apiKey;

        ResponseEntity<WeatherApiResponse> response =
                restTemplate.getForEntity(url, WeatherApiResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Weather external service responded with " + response.getStatusCode());
        }

        WeatherApiResponse body = response.getBody();
        if (body == null || body.list() == null || body.list().isEmpty()) {
            throw new RuntimeException("Weather forecast response is empty");
        }

        // -------------------------------------------------------------
        // Βρες το forecast που είναι πιο κοντά στο ζητούμενο dateTime
        // -------------------------------------------------------------
        var targetEpoch = dateTime.toEpochSecond(ZoneOffset.UTC);

        var bestMatch = body.list().stream()
                .min((a, b) -> {
                    long diffA = Math.abs(a.dt() - targetEpoch);
                    long diffB = Math.abs(b.dt() - targetEpoch);
                    return Long.compare(diffA, diffB);
                })
                .orElseThrow(() -> new RuntimeException("No forecast entries found"));

        // -------------------------------------------------------------
        // Μετατροπή σε WeatherInfo (το DTO που στέλνουμε στο Service Layer)
        // -------------------------------------------------------------
        return new WeatherInfo(
                body.city().name(),
                bestMatch.weatherData().temp(),
                bestMatch.weatherData().humidity(),
                bestMatch.weather().get(0).description()
        );
    }
}
