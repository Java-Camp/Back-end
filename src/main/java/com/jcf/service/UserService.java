package com.jcf.service;


import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser (User user){
        System.out.println(user.getFirstName() + " " + user.getLastName()+ " "
                + " " + user.getEmail() + " " + user.getPassword());

        //userRepository.save(user);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setFirstName("Anton");
        newUser.setLastName("Frolov");
        newUser.setEmail("avcod@gmail.com");
        newUser.setPassword("1234");
        return newUser;
    }
}
