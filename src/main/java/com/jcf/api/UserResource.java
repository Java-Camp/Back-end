package com.jcf.api;

import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.service.UserService;
import com.jcf.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }


//    @GetMapping("")
//    public ResponseEntity<List<User>> getUsers() {
//        return ResponseEntity.ok().body(userService.getUsers());
//    }

    @GetMapping("")
    public User getUser() {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserById(userEmail);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody UserVO vo){
        return ResponseEntity.ok(userService.updateUser(vo));
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody UserVO vo) {
        return ResponseEntity.ok(userService.saveUser(vo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }

}
