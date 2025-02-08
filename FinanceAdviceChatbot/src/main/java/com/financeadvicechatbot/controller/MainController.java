package com.financeadvicechatbot.controller;

import com.financeadvicechatbot.dto.ChatbotInfoDto;
import com.financeadvicechatbot.model.User;
import com.financeadvicechatbot.service.OpenAiService;
import com.financeadvicechatbot.service.UserDetails;
import com.financeadvicechatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() { return "index";}

    @RequestMapping("/financeAdvice")
    public String financeAdvise(@AuthenticationPrincipal User user, Model model) {
        //User is currently not an AuthenticationPrincipal
        //Need to find a way to get the user currently logged in
        ChatbotInfoDto chatbotInfo = userService.getChatbotInfo(user);
        String chatbotOutput = openAiService.getChatbotResponse(chatbotInfo);
        model.addAttribute("chatbotOutput", chatbotOutput);
        return "financeAdvise";
    }
}
