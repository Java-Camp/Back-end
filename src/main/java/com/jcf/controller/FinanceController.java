package com.jcf.controller;

import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/deleteUser")
    public ResponseEntity deleteTest(@RequestParam("id") Long id){
        return userRepository.delete(id);
    }

    @PostMapping("/saveUser")
    public void save(@RequestBody User user){
        userRepository.saveOrUpdate(user);
    }
}