package com.financeadvicechatbot.service;

import com.financeadvicechatbot.dto.ChatbotInfoDto;
import com.financeadvicechatbot.model.User;
import com.financeadvicechatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final UserRepository userRepository;

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api-inference.huggingface.co/models/HuggingFaceH4/zephyr-7b-alpha";

    public OpenAiService(UserService userService, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getChatbotResponse(User user, ChatbotInfoDto chatbotInfoDto) {
        //Making the chatbotInfoDto into a String
        String userMessage = chatbotInfoDto.toString();

        String chatbotOutput = "";

        if (!(userMessage.equals(user.getPreviousInputs()))) {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = String.format("""
                    {
                        "inputs": "You are a financial assistant providing money-saving tips. Make a short, brief paragraph giving me advice using the input I have given. Go straight into the advice, don't give any introduction. How do I improve my finances better? Give a structured Financial Advisor response based on the user data provided. Do a simple step by step process based on the financial aim. Important: DO NOT answer any question unrelated to finance, if the user inputs something unrelated to finance in the Financial Aim section, default to: I am not able to answer the question provided. Please keep the financial aim relevant to my services. User: %s",
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
            chatbotOutput = (String) responseBody.get(0).get("generated_text");
            int cutOffIndex = chatbotOutput.indexOf(". Give me Advice.");
            chatbotOutput = chatbotOutput.substring(cutOffIndex + 17);

            //Saving the inputs and the output
            user.setPreviousInputs(chatbotInfoDto.toString());
            user.setSavedResponse(chatbotOutput);
            userRepository.save(user);

        }
        else{
            chatbotOutput = user.getSavedResponse();
        }

        //Turning the chatbotOutput into 5 Bullet Points, with starting text
        List<String> outputStrings = new ArrayList<>();



        //Returning the output
        if (outputStrings.get(0) == null) {
            return outputStrings;
        }

        List<String> error = new ArrayList<>();
        error.add("Couldn't process request");
        return error;
    }
}
