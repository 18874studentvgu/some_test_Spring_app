package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //map the root endpoint to this thing
    @GetMapping("/")
    public String homepageString() {
        // Thymeleaf will return the request with "resources/template/index.html"
        return "index";
    }
    
}
