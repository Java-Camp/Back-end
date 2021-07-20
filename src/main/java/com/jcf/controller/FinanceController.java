package com.jcf.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class FinanceController {
    @GetMapping
    public String main(
            @RequestParam(name = "name", required = false, defaultValue = "user") String name,
            Model model
    ){
        model.addAttribute("name", name);
        return "Hello, " + name;
    }
}
