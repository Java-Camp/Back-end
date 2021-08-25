package com.jcf.service;

import com.jcf.persistence.model.User;
import com.jcf.vo.UserVO;

import java.util.List;

public interface UserService {
    User saveUser(UserVO vo);
    User getUserByEmail(String  username);
    User getUserById(String  id);
    List<User> getUsers();
    void delete(Long id);
    User updateUser(UserVO vo);
}
