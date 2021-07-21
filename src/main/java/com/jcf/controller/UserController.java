package com.jcf.controller;


import com.jcf.persistence.model.dto.request.UserAddDto;
import com.jcf.persistence.model.dto.response.ResponseDto;
import com.jcf.persistence.model.dto.response.UserResponseDto;
import com.jcf.persistence.model.entity.User;
import com.jcf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto<UserResponseDto>> createUser (@RequestBody UserAddDto dto){
       return ResponseEntity.ok(userService.createUser(dto));

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserById (@PathVariable("userId") Long userId){
       return ResponseEntity.ok(userService.getById(userId));

    }
}
