package com.jcf.service;


import com.jcf.persistence.model.User;
import com.jcf.vo.UserVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity saveUser(UserVO vo);
    User getUserByEmail(String  username);
    User getUserById(Long  id);
    List<User> getUsers();
    ResponseEntity delete(Long id);
}
