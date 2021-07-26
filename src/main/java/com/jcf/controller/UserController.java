package com.jcf.controller;




import com.jcf.exceptions.IncorrectAcountTypeException;
import com.jcf.exceptions.LockedAccessException;
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


    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
     //   return ResponseEntity.ok(userService.createUser(user));
    throw new IncorrectAcountTypeException("Incorrect AcountType");
    }
}
