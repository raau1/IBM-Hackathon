package com.financeadvicechatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api-inference.huggingface.co/models/HuggingFaceH4/zephyr-7b-alpha";

    public String getChatbotResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("""
        {
            "inputs": "You are a financial assistant providing money-saving tips. User: %s",
            "parameters": {
                "max_length": 200,
                "temperature": 0.7
            }
        }
        """, userMessage);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<List> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, List.class);

        List<Map<String, Object>> responseBody = response.getBody();
        if (responseBody != null && !responseBody.isEmpty()) {
            return responseBody.get(0).get("generated_text").toString();
        }

        return "Sorry, I couldn't process your request.";
    }
}
