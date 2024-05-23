package com.growing.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALARM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 알람 번호

    @Column(name = "TITLE")
    private String title; // 알람 제목

    @Column(name = "CONTENTS")
    private String contents; // 알람 내용

    @Column(name = "READFLAG")
    private boolean readFlag; // 읽음, 읽지 못함 깃발
}
