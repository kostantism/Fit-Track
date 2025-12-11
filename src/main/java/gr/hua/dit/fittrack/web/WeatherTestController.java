package gr.hua.dit.fittrack.web;

import gr.hua.dit.fittrack.core.port.WeatherPort;
import gr.hua.dit.fittrack.core.port.impl.dto.WeatherInfo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/weather")
public class WeatherTestController {

    private final WeatherPort weatherPort;

    public WeatherTestController(WeatherPort weatherPort) {
        this.weatherPort = weatherPort;
    }

    @GetMapping("/forecast")
    public WeatherInfo getWeather(
            @RequestParam String city,
            @RequestParam String dateTime
    ) {
        LocalDateTime dt = LocalDateTime.parse(dateTime); // format: 2025-12-20T15:00

        return weatherPort.getForecast(city, dt);
    }
}

