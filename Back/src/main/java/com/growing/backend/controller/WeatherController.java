package com.growing.backend.controller;

import com.growing.backend.dto.WeatherDataDTO;
import com.growing.backend.entity.WeatherData;
import com.growing.backend.entity.WeatherTemperature;
import com.growing.backend.repository.DateTemperatureRepository;
import com.growing.backend.repository.WeatherDataRepository;
import com.growing.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherDataRepository weatherDataRepository;
    private final DateTemperatureRepository dateTemperatureRepository;

    @GetMapping("/all")
    public List<WeatherDataDTO> weather() {
        List<WeatherDataDTO> weatherDataList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        List<LocalDate> dates = Arrays.asList(today, tomorrow, dayAfterTomorrow);

        for (int i = 0; i < dates.size(); i++) {
            LocalDate date = dates.get(i);

            WeatherData weatherData = weatherDataRepository.findByFcstDate(date)
                    .orElseThrow(() -> new RuntimeException("Weather data not found for date: " + date));

            WeatherTemperature weatherTemperature = dateTemperatureRepository.findByFcstDate(date)
                    .orElseThrow(() -> new RuntimeException("Temperature data not found for date: " + date));

            WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
            weatherDataDTO.setDay(i + 1);
            weatherDataDTO.setTime(LocalTime.now().getHour());
            weatherDataDTO.setMaxTemp(weatherTemperature.getTmx());
            weatherDataDTO.setMinTemp(weatherTemperature.getTmn());
            weatherDataDTO.setCurrentTemp(weatherData.getTemperature());
            weatherDataDTO.setHumidity(weatherData.getHumidity());
            weatherDataDTO.setWeatherCode(weatherData.getWeatherCode());
            weatherDataDTO.setRain(weatherData.getRain());

            weatherDataList.add(weatherDataDTO);
        }

        return weatherDataList;
    }
}
