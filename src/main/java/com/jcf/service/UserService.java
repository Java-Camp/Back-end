package com.jcf.service;


import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        return user;
    }

    public User findById(Long id) {

        return userRepository.findById(id).get();
    }

    public ResponseEntity delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("No entity with such id");
        }

        userRepository.delete(id);
        if (userRepository.findById(id).isPresent()) {
            return ResponseEntity.status(405).body("Delete is not working");
        }

        return ResponseEntity.status(200).body("Entity was deleted");
    }
   
    public void saveOrUpdate(User user) {
        userRepository.saveOrUpdate(user);
    }
}
