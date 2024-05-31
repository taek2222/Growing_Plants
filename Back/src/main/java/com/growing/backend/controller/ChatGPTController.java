package com.growing.backend.controller;

import com.growing.backend.dto.response.chatGPT.ChatGPTResultResponse;
import com.growing.backend.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    // 식물 이름 기반 시스템 환경 기준치 추천 AI
    @GetMapping("/chat/{plant_name}")
    public ResponseEntity<ChatGPTResultResponse> getAIAnalysis(@PathVariable("plant_name") String plantName) {
        String response = chatGPTService.processAIResponse(plantName);
        return new ResponseEntity<>(new ChatGPTResultResponse(response), HttpStatus.OK);
    }
}