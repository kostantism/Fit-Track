package gr.hua.dit.fittrack.core.port.impl;

import gr.hua.dit.fittrack.config.RestApiClientConfig;
import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherApiResponse;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherPortImpl implements WeatherPort {

    private final RestTemplate restTemplate;

    public WeatherPortImpl(RestTemplate restTemplate) {
        if(restTemplate==null) throw new NullPointerException();

        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherInfo getCurrentWeather(final String cityName) {
        if(cityName==null) throw new NullPointerException();
        if(cityName.isBlank()) throw new IllegalArgumentException();

        final String baseUrl = RestApiClientConfig.BASE_URL;
        final String apiKey = RestApiClientConfig.WEATHER_API_KEY;

        // URL π.χ. https://api.openweathermap.org/data/2.5/weather?q=Athens&appid=xxxx&units=metric
        final String url = baseUrl + "/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + apiKey;

        ResponseEntity<WeatherApiResponse> response =
                restTemplate.getForEntity(url, WeatherApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            WeatherApiResponse body = response.getBody();
            if (body == null) throw new NullPointerException();

            return new WeatherInfo(
                    body.name(),
                    body.weatherData().temp(),
                    body.weatherData().humidity(),
                    body.weather().get(0).description()
            );
        }

        throw new RuntimeException("Weather external service responded with " + response.getStatusCode());
    }
}
