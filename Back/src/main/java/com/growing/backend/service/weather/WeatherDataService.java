package com.growing.backend.service.weather;

import com.google.gson.*;
import com.growing.backend.entity.WeatherData;
import com.growing.backend.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherTemperatureService weatherTemperatureService;
    private static final Logger logger = LoggerFactory.getLogger(WeatherDataService.class);

    // 서비스 키
    @Value("${serviceKey}")
    private String serviceKey;

    static String baseDate;
    static String baseTime;

    @Transactional
    @Scheduled(cron = "0 15 * * * ?") // 매시간 15분 작동
    public void getWeatherData() throws IOException {
        // baseDate, baseTime 시간, 날짜 설정
        setBaseTimeDate();

        // 기상청 요청
        String urlStr = UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?")
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "1000")
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", "76")
                .queryParam("ny", "121")
                .toUriString();

        // 요청 Url 생성
        URL url = new URL(urlStr);

        // Url 기반 요청
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // JSON 데이터 버퍼 저장
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String content = br.readLine();

        br.close();
        conn.disconnect();

        addWeatherData(content);
    }

    // API 요청 시간 및 날짜 설정
    private void setBaseTimeDate() {
        // Asia/Seoul 기준 날짜 시간 설정
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 현재 측정 시간에서 시간, 분으로 변수 생성
        int hour = now.getHour();
        int minute = now.getMinute();

        // 시간 분을 기점으로 localTime 변수 및 2시 기점 생성
        LocalTime localTime = LocalTime.of(hour, minute);
        LocalTime standardTime = LocalTime.of(2, 0);

        // 시간이 2시를 넘는지 체크
        if(localTime.isAfter(standardTime)) {
            int settingHour = ((hour+1)/3) * 3 - 1;
            baseTime = String.format("%02d00", settingHour);
            baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        else {
            baseTime = "2300";
            baseDate = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        logger.info("[setBaseTimeDate] 기상청 baseTime, Date 확인 : {} {}", baseDate, baseTime);
    }

    // 파싱 데이터 DB 삽입 코드
    private void addWeatherData(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject response = jsonObject.getAsJsonObject("response");
        JsonObject body = response.getAsJsonObject("body");
        JsonArray items = body.getAsJsonObject("items").getAsJsonArray("item");

        // 측정 날짜, 시간 변수
        LocalDate fcstDate;
        LocalTime fcstTime;

        // 날씨 코드 및 기상청의 다양한 값 변수
        int weatherCode;
        int temperature, humidity, skyWeather, rainWeather, rain;

        // 변수 초기화
        temperature = humidity = skyWeather = rainWeather = rain = -1;

        // 현재 시간 측정 및 "HH00 - 12:00" 으로 포멧
        LocalTime localTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
        String hour = localTime.format(DateTimeFormatter.ofPattern("HH00"));

        // 최고, 최저 온도 기록
        if (hour.equals("0200"))
            weatherTemperatureService.setWeatherTemperature(items);

        // 파싱 데이터 탐색
        for (JsonElement itemElement : items) {
            JsonObject item = itemElement.getAsJsonObject();
            String category = item.getAsJsonPrimitive("category").getAsString();
            JsonPrimitive valuePrimitive = item.getAsJsonPrimitive("fcstValue");

            // 값이 NULL 및 시간 측정이 현재랑 맞지 않으면 continue
            if (valuePrimitive == null) continue;
            if (!item.getAsJsonPrimitive("fcstTime").getAsString().equals(hour)) continue;

            switch (category) {
                case "TMP": // 온도
                    temperature = Integer.parseInt(valuePrimitive.getAsString());
                    break;
                case "SKY": // 날씨 상태
                    skyWeather = Integer.parseInt(valuePrimitive.getAsString());
                    break;
                case "PTY": // 비, 눈 상태
                    rainWeather = Integer.parseInt(valuePrimitive.getAsString());
                    break;
                case "POP": // 강수 확률
                    rain = Integer.parseInt(valuePrimitive.getAsString());
                    break;
                case "REH":  // 습도
                    humidity = Integer.parseInt(valuePrimitive.getAsString());
                    break;
            }

            // 모든 데이터 수집 완료 시 데이터 베이스 전달
            if (temperature != -1 && skyWeather != -1 && rainWeather != -1 && rain != -1 && humidity != -1) {
                // Date, Time 날짜 데이터 변환
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

                // 변환 기반으로 데이터 등록
                fcstDate = LocalDate.parse(item.getAsJsonPrimitive("fcstDate").getAsString(), dateFormatter); // 측정 날짜
                fcstTime = LocalTime.parse(item.getAsJsonPrimitive("fcstTime").getAsString(), timeFormatter); // 측정 시간

                // 강수코드 변환
                weatherCode = getWeatherCode(skyWeather, rainWeather);

                WeatherData weatherData = weatherDataRepository.findByFcstDateAndFcstTime(fcstDate, fcstTime)
                        .orElse(new WeatherData());

                weatherData.setFcstDate(fcstDate);
                weatherData.setFcstTime(fcstTime);
                weatherData.setTemperature(temperature);
                weatherData.setHumidity(humidity);
                weatherData.setWeatherCode(weatherCode);
                weatherData.setRain(rain);

                // 측정 데이터 DB 삽입
                weatherDataRepository.save(weatherData);

                // 측정 초기화
                temperature = humidity = skyWeather = rainWeather = rain = -1;
            }
        }
    }

    // 강수 코드
    private int getWeatherCode(int skyWeather, int rainWeather) {
        int weatherCode = -1;

        // 강수형태 코드 기반 분할
        if(rainWeather == 0) {
            // 강수형태 NO - 날씨 탐색
            weatherCode = switch (skyWeather) {
                case 1 -> 1;
                case 3 -> 2;
                case 4 -> 3;
                default -> weatherCode;
            };
        } else {
            // 강수형태 ON - 강수 탐색
            weatherCode = switch (rainWeather) {
                case 1 -> 4;
                case 2 -> 5;
                case 3 -> 6;
                case 4 -> 7;
                default -> weatherCode;
            };
        }
        return weatherCode;
    }
}
