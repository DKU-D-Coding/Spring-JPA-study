package com.dku.springstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/api/usage")
    public String swaggerUi(){
        return "redirect:/swagger-ui/index.html";
    }

}
