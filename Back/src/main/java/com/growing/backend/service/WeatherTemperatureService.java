package com.growing.backend.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.growing.backend.entity.WeatherTemperature;
import com.growing.backend.repository.WeatherTemperatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherTemperatureService {
    private final WeatherTemperatureRepository weatherTemperatureRepository;

    // 최고, 최저 온도
    public void getDataTemperature(JsonArray items) {

        // 최고 최저 온도 변수 선언
        double tmx = -1;
        double tmn = -1;

        // 파싱 데이터 반복문
        for (JsonElement itemElement : items) {
            JsonObject item = itemElement.getAsJsonObject();
            String category = item.getAsJsonPrimitive("category").getAsString();
            JsonPrimitive valuePrimitive = item.getAsJsonPrimitive("fcstValue");

            switch (category) {
                // 최고 온도
                case "TMX":
                    tmx = Double.parseDouble(valuePrimitive.getAsString());
                    break;
                // 최저 온도
                case "TMN":
                    tmn = Double.parseDouble(valuePrimitive.getAsString());
                    break;
            }

            if(tmx != -1 & tmn != -1) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                LocalDate fcstDate = LocalDate.parse(item.getAsJsonPrimitive("fcstDate").getAsString(), dateFormatter); // 측정 날짜

                WeatherTemperature weatherTemperature = weatherTemperatureRepository.findByFcstDate(fcstDate)
                        .orElse(new WeatherTemperature());

                weatherTemperature.setFcstDate(fcstDate);
                weatherTemperature.setTmx(tmx);
                weatherTemperature.setTmn(tmn);

                weatherTemperatureRepository.save(weatherTemperature);
                tmx = tmn = -1;
            }
        }
    }
}
