package com.jcf.api;

import com.jcf.persistence.model.User;
import com.jcf.service.UserService;
import com.jcf.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class UserResource {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }



    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody UserVO vo) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(vo));
    }

    @PostMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }

}
