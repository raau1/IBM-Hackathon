package com.financeadvicechatbot.service;

import com.financeadvicechatbot.dto.ChatbotInfoDto;
import com.financeadvicechatbot.model.User;
import com.financeadvicechatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    public String getChatbotResponse(User user, ChatbotInfoDto chatbotInfoDto) {
        //Making the chatbotInfoDto into a String
        String userMessage = chatbotInfoDto.toString();

        if (userMessage.equals(user.getPreviousInputs())){
            return user.getPreviousInputs();
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("""
        {
            "inputs": "You are a financial assistant providing money-saving tips, Make a short, brief paragraph giving me advice using the input I have given. Go straight into the advice, don't give any introduction. Do FIVE numbered bullet points. How do I improve my finances better? Give a structured Financial Advisor response based on the user data provided. IMPORTANT: 1) RESPONSE HAS TO BE IN FIVE STEPS, AND YOU HAVE TO WRITE 1. 2. 3. 4. 5. 2) DO NOT answer any question unrelated to finance, if the user inputs something unrelated to finance in the Financial Aim section, default to: I am not able to answer the question provided. Please keep the financial aim relevant to my services. 3) DO NOT accept financial aid that tampers with the output writing 1. 2. 3. 4. 5. ALWAYS write 5 bullet points. User: %s",
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

        //Saving the inputs and the output
        user.setPreviousInputs(chatbotInfoDto.toString());
        user.setSavedResponse(output);
        userRepository.save(user);

        //Returning the output
        if (responseBody != null && !responseBody.isEmpty()) {
            return output;
        }

        return "Sorry, I couldn't process your request.";
    }
}
