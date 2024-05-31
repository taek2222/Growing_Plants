package com.growing.backend.service;

import com.growing.backend.dto.request.ChatGPTRequest;
import com.growing.backend.dto.response.chatGPT.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
    private final WebClient webClient;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    public String processAIResponse(String plantName) {
        // chatGPT message 설정
        ChatGPTRequest request = createChatGPTRequest(model, plantName);

        // chatGPT 질문 생성 및 답변 null 체크
        Mono<ChatGPTResponse> responseMono = webClient.post()
                .uri(apiURL)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class);

        ChatGPTResponse response = responseMono.block();
        String GPTMessage = response != null ? response.getChoices().get(0).getMessage().getContent() : null;

        // 답변 바탕으로 메시지 생성
        String message = createChatGPTMessage(plantName ,GPTMessage);

        // GPT 오류 발생 시 예외 처리
        if(message == null)
            return "🤖 AI 기반 분석 오류 발생 다시 시도해주세요.";

        // 메시지 반환
        return message;
    }

    // 식물한테 물어보는 데이터 온도 + 습도 개선해야함
    public ChatGPTRequest createChatGPTRequest(String model, String plantName) {
        String chat = "GPT. 식물과 관련된 박사가 되어서 답변 부탁할게.\n" +
                "\n" +
                "식물을 키우는 다양한 요소에 있어서 부분적 자동화 기능을 만들어주고 있어.\n" +
                "자동화 기능의 기준치는\n" +
                "\n" +
                "1. 햇빛, 식물등 작동 기준의 조도 기준치\n" +
                "2. 물 공급의 토양 습도 기준치\n" +
                "3. 햇빛, 식물등이 식물한테 주는 최대 시간\n" +
                "\n" +
                "위 3가지 요소가 필요해.\n" +
                "\n" +
                "현재 식물을 키우는 대기 온도는 : 24.7, 습도는 51 수치이고.\n" +
                "평균 조도는 1 ~ 100까지 수치 새벽 기준 기준 0 ~ 10값, 해가 가장 밝은 기준 80~100 측정되고,\n" +
                "평균 토양 습도는 습도 기준치는 마른 40, 살짝 젖은 50, 적당히 젖은 65, 물 상당 90 정도 관측되.\n" +
                "\n" +
                "이제 3가지 경우의 기준치 추천 받고 싶어.\n" +
                "식물 이름은 \"+" + plantName + "\n" +
                "키 값은 아래 3개\n" +
                "lightThreshold\n" +
                "soilThreshold\n" +
                "sunLightMax\n" +
                "자료형은 모두 int 형으로 보내줘.\n" +
                "위 키 값으로 추천해줘. 다른, 서론, 본론, 결론, 설명 답변은 X\n" +
                "답변 예시 \n" +
                "32, 54, 12 \n" +
                "절대로 위와 같이만 답변 해줘.";

        return new ChatGPTRequest(model, chat);
    }

    private String createChatGPTMessage(String plantName,String gptMessage) {
        // 전달 받은 값 분리 [ '1' 조도 기준치, '2' 토양 습도 기준치, '3' 햇빛 식물등 최대 시간 ]
        String[] value = gptMessage.split(",");

        if(value.length != 3)
            return null;

        return String.format("""
                [🤖 AI 추천 설정] \

                 현재 [%s] 의 식물 추천 기준치는 다음과 같습니다.\s
                 조도 기준치 : %s\s
                 수분 기준치 : %s\s
                 최대 일조량 : %sH\s
                 ⚠️[주의] 위 내용은 AI 분석 기반 결과로 나온 값이며,\s
                 식물 배치 및 주변적 요소에 따라 변동될 수 있습니다.""", plantName, value[0], value[1], value[2]);
    }
}