package com.jcf.service;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User userEntity) {
//        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
//        userEntity.setRoleEntity(userRole);
        userEntity.setFirstName(userEntity.getFirstName());
        userEntity.setLastName(userEntity.getLastName());
        userEntity.setEmail(userEntity.getEmail());
        userEntity.setRole(userEntity.getRole());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.saveOrUpdate(userEntity);
    }

    public User findByLogin(String email) {
        return userEntityRepository.findByEmail(email);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
