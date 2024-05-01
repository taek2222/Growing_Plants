package com.growing.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlantInfoRequestDTO {
    private int id; // 식물 아이디
    private String name; // 식물 이름
    private LocalDate date; // 식물 성장 일자
}