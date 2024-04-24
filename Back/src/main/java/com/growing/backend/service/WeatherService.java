package com.growing.backend.service;

import com.google.gson.*;
import com.growing.backend.dto.WeatherDataDTO;
import com.growing.backend.entity.WeatherData;
import com.growing.backend.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherDataRepository weatherDataRepository;

    static String baseDate;
    static String baseTime;

    public String getWeatherData() throws IOException {
        // baseDate, baseTime 시간, 날짜 설정
        setBaseTimeBaseDate();

        String urlStr = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?")
                .queryParam("serviceKey", "EViTSUUmhHodg8yW4mcVr9xshH6g5oJDd0be1zYo0R7T2M7TouEiusZdV3Wnj689jQaTLJ7qX9Cn6gZmxUyXxw==")
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1000")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", "76")
                .queryParam("ny", "121")
                .toUriString();

        URL url = new URL(urlStr);
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        // close connections
        in.close();
        conn.disconnect();

        WeatherData weatherData = parseWeatherData(content.toString());
        System.out.println(weatherData);
        weatherDataRepository.save(weatherData);

        return content.toString();
    }

    // API 요청 시간 및 날짜 설정
    private void setBaseTimeBaseDate() {
        // Asia/Seoul 기준 날짜 시간 설정
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 현재 측정 시간에서 시간, 분으로 변수 생성
        int hour = now.getHour();
        int minute = now.getMinute();

        // 시간 분을 기점으로 localTime 변수 및 [2시 10분] 기점 생성
        LocalTime localTime = LocalTime.of(hour, minute);
        LocalTime standardTime = LocalTime.of(2, 10);

        // 시간이 [2시 10분]을 넘거나 혹은 같으면 True 아니면 False
        if(localTime.isAfter(standardTime) || localTime.equals(standardTime)) {
            if(minute >= 10)
                hour++;

            int settingHour = (hour/3) * 3 - 1;
            baseTime = String.format("%02d00", settingHour);
            baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        else {
            baseTime = "23:00";
            baseDate = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
    }

    private WeatherData parseWeatherData(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = jsonObject.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonArray items = body.getAsJsonObject("items").getAsJsonArray("item");

        String baseDate = null;
        String baseTime = null;
        int temperature = 0;
        int humidity = 0;
        int weatherCode = 0;
        int rain = 0;

        for (JsonElement itemElement : items) {
            JsonObject item = itemElement.getAsJsonObject();
            String category = item.getAsJsonPrimitive("category").getAsString();
            JsonPrimitive valuePrimitive = item.getAsJsonPrimitive("fcstValue");

            if (valuePrimitive == null) continue;

            switch (category) {
                case "TMP": // 온도
                    temperature = Integer.parseInt(valuePrimitive.getAsString());
                    break;
//                case "SKY": // 날씨 상태
                case "POP": // 강수 확률
                    rain = Integer.parseInt(valuePrimitive.getAsString());
                    break;
                case "REH":  // 습도
                    humidity = Integer.parseInt(valuePrimitive.getAsString());
                    break;
            }

            if (baseDate == null || baseTime == null) {
                baseDate = item.getAsJsonPrimitive("baseDate").getAsString();
                baseTime = item.getAsJsonPrimitive("baseTime").getAsString();
            }
        }

        System.out.println(baseDate + " " + baseTime + " " + temperature + " " + humidity + " " + weatherCode + " " + rain);
        return new WeatherData(baseDate, baseTime, temperature, humidity, weatherCode, rain);
    }
}
