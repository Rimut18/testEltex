package com.example.testeltex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Value("${frontend.api.url}")
    private String frontendApiUrl;

    @GetMapping("/")
    public String userTable(Model model) {
        model.addAttribute("apiUrl", frontendApiUrl);
        return "UserTable";
    }
}
