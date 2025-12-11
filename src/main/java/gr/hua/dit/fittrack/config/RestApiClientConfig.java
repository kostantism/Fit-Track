package gr.hua.dit.fittrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestApiClientConfig {

    // @future Get me from application properties!
    public static final String WEATHER_BASE_URL = "https://api.openweathermap.org";
    public static final String WEATHER_API_KEY = "api key";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
