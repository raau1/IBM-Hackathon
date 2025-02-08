package com.financeadvicechatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String getChatbotResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String requestBody = """
        {
            "model": "gpt-4",
            "messages": [
                {"role": "system", "content": "You are a financial assistant providing money-saving tips."},
                {"role": "user", "content": "%s"}
            ]
        }
        """.formatted(userMessage);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, Map.class);

        // Extract response from OpenAI
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("choices")) {
            return ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) responseBody.get("choices")).get(0)).get("message")).get("content").toString();
        }

        return "Sorry, I couldn't process your request.";
    }
}
