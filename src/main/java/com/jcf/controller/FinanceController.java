package com.jcf.controller;

import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class FinanceController {


    private final UserRepository userRepository;

    @Autowired
    public FinanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public String main(@RequestParam("id") Long id) {
        return userRepository.findById(id).get().toString();
    }
}
