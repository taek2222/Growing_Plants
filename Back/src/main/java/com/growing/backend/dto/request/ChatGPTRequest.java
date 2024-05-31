package com.growing.backend.dto.request;

import com.growing.backend.dto.response.chatGPT.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
// Client - Spring
// Server - ChatGPT
// 식물 AI 기준치 추천 GET(Back -> ChatGPT) 요청 DTO
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String contents) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", contents));
    }
}