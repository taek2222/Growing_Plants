package com.growing.backend.dto.response.chatGPT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Client - ChatGPT
// Server - Spring
// 식물 AI 기준치 추천 GET(ChatGPT -> Back) 요청 DTO
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }
}