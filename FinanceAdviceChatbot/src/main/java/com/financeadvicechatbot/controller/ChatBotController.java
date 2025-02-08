package com.example.financialchatbot.controller;

import com.example.financialchatbot.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatbotController {

    private final OpenAiService openAiService;

    public ChatbotController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping
    public String chatWithBot(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");
        return openAiService.getChatbotResponse(userInput);
    }
}
