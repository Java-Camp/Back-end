package com.jcf.controller;


import com.jcf.persistence.model.User;
import com.jcf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/deleteUser")
    public ResponseEntity deleteTest(@RequestParam("id") Long id){
        return userService.delete(id);
    }

    @PostMapping("/saveUser")
    public void save(@RequestBody User user){
        userService.saveOrUpdate(user);
    }
}
