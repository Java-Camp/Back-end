package com.jcf.service;


import com.jcf.persistence.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserByEmail(String  username);
    User getUserById(Long  id);
    List<User> getUsers();

}
