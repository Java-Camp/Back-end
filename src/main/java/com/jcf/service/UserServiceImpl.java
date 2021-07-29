package com.jcf.service;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else log.error("User found: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public ResponseEntity saveUser(UserVO vo) {
        log.info("Saving new user to database");
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        try {
            User user = new User();
            if (Objects.isNull(vo.getFirstName()))
                return ResponseEntity.status(422).body("First name is null");
            if (Objects.isNull(vo.getLastName()))
                return ResponseEntity.status(422).body("Last Name is null");
            if (Objects.isNull(vo.getEmail()))
                return ResponseEntity.status(422).body("Email is null");
            if (Objects.isNull(vo.getPassword()))
                return ResponseEntity.status(422).body("Password is null");

            user.setFirstName(vo.getFirstName());
            user.setLastName(vo.getLastName());
            user.setEmail(vo.getEmail());
            user.setPassword(vo.getPassword());
            user.setRole(vo.getRole());

            User user1 = userRepo.saveOrUpdate(user);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
            return ResponseEntity.created(uri).body(user1);
        }
        catch (Exception ex){
            platformTransactionManager.rollback(status);
            return ResponseEntity.status(409).body(ex);
        }
    }

    @Override
    public User getUserByEmail(String username) {
        log.info("Getting user from database");
        return userRepo.findByEmail(username);
    }

    @Override
    public User getUserById(Long id) {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty())
            throw new IllegalArgumentException("No such user!");
        return byId.get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Getting all users from database");
        return null;
    }

    @Override
    public ResponseEntity delete(Long id) {
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        try{
            if (userRepo.findById(id).isEmpty())
                return ResponseEntity.status(404).body("No entity with such id");

            userRepo.delete(id);

            if (userRepo.findById(id).isPresent())
                return ResponseEntity.status(405).body("Delete is not working");

            platformTransactionManager.commit(status);
            return ResponseEntity.status(200).body("Entity was deleted");
        }
        catch (Exception ex){
            platformTransactionManager.rollback(status);
            return ResponseEntity.status(409).body(ex);
        }
    }

}
