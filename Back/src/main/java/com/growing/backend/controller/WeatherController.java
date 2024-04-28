package com.growing.backend.controller;

import com.growing.backend.dto.WeatherDataDTO;
import com.growing.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/all")
    public List<WeatherDataDTO> weather() {
        return weatherService.getWeatherData();
    }
}
