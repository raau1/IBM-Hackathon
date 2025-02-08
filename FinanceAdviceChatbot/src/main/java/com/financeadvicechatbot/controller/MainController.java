package com.financeadvicechatbot.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {
    @RequestMapping("/")
    public String index() { return "index";}
}
