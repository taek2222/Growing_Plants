package com.growing.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 식물 정보 GET(Back -> Front) 요청 DTO
public class PlantDTO {
    private int plantId; // 아이디
    private String plantName; // 이름
    private String image; // 이미지
    private int date; // 성장 일자
}