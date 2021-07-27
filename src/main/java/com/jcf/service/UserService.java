package com.jcf.service;


import com.jcf.persistence.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String  username);
    List<User> getUsers();

}
