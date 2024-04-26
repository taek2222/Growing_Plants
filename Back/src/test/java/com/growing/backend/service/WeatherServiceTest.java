package com.growing.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeatherData() throws IOException {
        String result = weatherService.getWeatherData();
        System.out.println(result);
    }
}