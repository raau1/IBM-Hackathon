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

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("""
        {
            "inputs": "You are a financial assistant providing money-saving tips. Make a short, brief paragraph giving me advice using the input I have given. Go straight into the advice, don't give any introduction. User: %s",
            "parameters": {
                "max_length": 200,
                "temperature": 0.7
            }
        }
        """, userMessage);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<List> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, List.class);

        //Making the output from the AI understandable
        List<Map<String, Object>> responseBody = response.getBody();
        String output = (String) responseBody.get(0).get("generated_text");
        int cutOffIndex = output.indexOf(". Give me Advice.");
        output = output.substring(cutOffIndex+17);

        //Returning the output
        if (responseBody != null && !responseBody.isEmpty()) {
            return output;
        }

        return "Sorry, I couldn't process your request.";
    }
}
