package com.growing.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmResponse {
    private Long id; // 알람 번호
    private String title; // 알람 제목
    private String contents; // 알람 내용
    private boolean readFlag; // 읽음, 읽지 못함 깃발
}
