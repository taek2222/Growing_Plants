package com.growing.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
// 물 공급 GET(Back -> Front) 요청 DTO
public class PlantWaterSupplyDTO {
    private int listId;
    private LocalDate supplyDate;
    private LocalTime supplyTime;
}