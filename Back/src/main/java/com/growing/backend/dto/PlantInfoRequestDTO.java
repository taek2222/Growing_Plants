package com.growing.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlantInfoRequestDTO {
    private String name; // 식물 이름
    private LocalDate Data; // 식물 성장 일자
}
