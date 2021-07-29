package com.jcf.api;

import com.jcf.persistence.model.User;
import com.jcf.service.UserService;
import com.jcf.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity saveUser(@RequestBody UserVO vo) {
        return userService.saveUser(vo);
    }

    @PostMapping("/users/delete/{id}")
    public ResponseEntity deleteTest(@PathVariable Long id){
        return userService.delete(id);
    }

}
