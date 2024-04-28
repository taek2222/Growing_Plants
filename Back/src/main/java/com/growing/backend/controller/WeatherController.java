package com.growing.backend.controller;

import com.growing.backend.dto.WeatherDataDTO;
import com.growing.backend.entity.WeatherData;
import com.growing.backend.entity.WeatherTemperature;
import com.growing.backend.repository.WeatherTemperatureRepository;
import com.growing.backend.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherTemperatureRepository weatherTemperatureRepository;

    @GetMapping("/all")
    public List<WeatherDataDTO> weather() {
        // 리스트 형태로 저장
        List<WeatherDataDTO> weatherDataList = new ArrayList<>();

        // 오늘, 내일, 내일 모레 날짜
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        // 시간 데이터
        LocalTime hour = LocalTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.HOURS);

        // 배열로 저장
        List<LocalDate> dates = Arrays.asList(today, tomorrow, dayAfterTomorrow);

        // 반복문으로 리스트 저장
        for (int i = 0; i < dates.size(); i++) {
            LocalDate date = dates.get(i);

            // 날짜, 시간 기준 최고, 최저 기온 탐색
            WeatherData weatherData = weatherDataRepository.findByFcstDateAndFcstTime(date, hour)
                    .orElseThrow(() -> new RuntimeException("Weather data not found for date: " + date + hour));

            // 날짜 기준 최고, 최저 기온 탐색
            WeatherTemperature weatherTemperature = weatherTemperatureRepository.findByFcstDate(date)
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
