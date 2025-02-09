package com.financeadvicechatbot.service;

import com.financeadvicechatbot.dto.ChatbotInfoDto;
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

    public String getChatbotResponse(ChatbotInfoDto chatbotInfoDto) {
        //Making the chatbotInfoDto into a String
        String userMessage = chatbotInfoDto.toString();
        System.out.println(userMessage);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("""
        {
            "inputs": "How do I improve my finances better? Give a structured Financial Advisor response based on the user data provided. Do a simple step by step process based on the financial aim. Important: DO NOT answer any question unrelated to finance, if the user inputs something unrelated to finance in the Financial Aim section, default to: I am not able to answer the question provided. Please keep the financial aim relevant to my services",
            "parameters": {
                "max_length": 100,
                "temperature": 0.5
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
