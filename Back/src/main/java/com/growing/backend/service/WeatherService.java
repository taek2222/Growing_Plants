package com.growing.backend.service;

import com.growing.backend.dto.WeatherDataDTO;
import com.growing.backend.entity.WeatherData;
import com.growing.backend.entity.WeatherTemperature;
import com.growing.backend.repository.WeatherDataRepository;
import com.growing.backend.repository.WeatherTemperatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherTemperatureRepository weatherTemperatureRepository;

    public WeatherDataDTO getWeatherData() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 시간 데이터
        LocalTime hour = LocalTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.HOURS);

        // 날짜, 시간 기준 최고, 최저 기온 탐색
        WeatherData weatherData = weatherDataRepository.findByFcstDateAndFcstTime(today, hour)
                .orElseThrow(() -> new RuntimeException("Weather data not found for date: " + today + hour));

        // 날짜 기준 최고, 최저 기온 탐색
        WeatherTemperature weatherTemperature = weatherTemperatureRepository.findByFcstDate(today)
                .orElseThrow(() -> new RuntimeException("Temperature data not found for date: " + today));

        return getWeatherDataDTO(weatherTemperature, weatherData);
    }

    public List<WeatherDataDTO> getWeatherDataList() {
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
        for (LocalDate date : dates) {
            // 날짜, 시간 기준 최고, 최저 기온 탐색
            WeatherData weatherData = weatherDataRepository.findByFcstDateAndFcstTime(date, hour)
                    .orElseThrow(() -> new RuntimeException("Weather data not found for date: " + date + hour));

            // 날짜 기준 최고, 최저 기온 탐색
            WeatherTemperature weatherTemperature = weatherTemperatureRepository.findByFcstDate(date)
                    .orElseThrow(() -> new RuntimeException("Temperature data not found for date: " + date));

            // 데이터 기준 DTO
            WeatherDataDTO weatherDataDTO = getWeatherDataDTO(weatherTemperature, weatherData);

            weatherDataList.add(weatherDataDTO);
        }
        return weatherDataList;
    }

    private WeatherDataDTO getWeatherDataDTO(WeatherTemperature weatherTemperature, WeatherData weatherData) {
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
        weatherDataDTO.setMonth(weatherData.getFcstDate().getMonthValue());
        weatherDataDTO.setDay(weatherData.getFcstDate().getDayOfMonth());
        weatherDataDTO.setTime(LocalTime.now().getHour());
        weatherDataDTO.setMaxTemp(weatherTemperature.getTmx());
        weatherDataDTO.setMinTemp(weatherTemperature.getTmn());
        weatherDataDTO.setCurrentTemp(weatherData.getTemperature());
        weatherDataDTO.setHumidity(weatherData.getHumidity());
        weatherDataDTO.setWeatherCode(weatherData.getWeatherCode());
        weatherDataDTO.setRain(weatherData.getRain());
        return weatherDataDTO;
    }
}