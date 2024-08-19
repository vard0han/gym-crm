package com.example.controller;

import com.example.actuators.CustomMetrics;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class startController {
    CustomMetrics customMetrics;

    public startController(CustomMetrics customMetrics) {
        this.customMetrics = customMetrics;
    }

    @GetMapping("/")
    public String startPage(){
        customMetrics.incrementRequestCounter();
        return "index";
    }
}
