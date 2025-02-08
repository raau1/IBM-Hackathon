package com.financeadvicechatbot.controller;

import com.financeadvicechatbot.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private OpenAiService openAiService;

    @RequestMapping("/")
    public String index() { return "index";}

    @RequestMapping("/financeAdvice")
    public String mortgageAdvise(Model model) {

        return "financeAdvise";
    }
}
