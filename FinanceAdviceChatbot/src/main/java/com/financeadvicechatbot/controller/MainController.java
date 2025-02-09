package com.financeadvicechatbot.controller;

import com.financeadvicechatbot.dto.ChatbotInfoDto;
import com.financeadvicechatbot.model.User;
import com.financeadvicechatbot.repository.UserRepository;
import com.financeadvicechatbot.service.OpenAiService;
import com.financeadvicechatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index() { return "index";}

    @RequestMapping("/financeAdvice")
    public String financeAdvise(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        ChatbotInfoDto chatbotInfo = userService.getChatbotInfo(user);
        String chatbotOutput = openAiService.getChatbotResponse(user, chatbotInfo);
        model.addAttribute("chatbotOutput", chatbotOutput);
        return "financeAdvise";
    }
}
