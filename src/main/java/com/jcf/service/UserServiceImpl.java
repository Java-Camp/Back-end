package com.jcf.service;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        else
            log.error("User found: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(UserVO vo) {
        log.info("Saving new user to database");
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        User user = new User();
        if (Objects.isNull(vo.getFirstName()))
            throw new IllegalArgumentException("First name is null");
        if (Objects.isNull(vo.getLastName()))
            throw new IllegalArgumentException("Last name is null");
        if (Objects.isNull(vo.getEmail()))
            throw new IllegalArgumentException("Email is null");
        if (Objects.isNull(vo.getPassword()))
            throw new IllegalArgumentException("Password is null");

        user.setFirstName(vo.getFirstName());
        user.setLastName(vo.getLastName());
        user.setEmail(vo.getEmail());
        user.setPassword(vo.getPassword());
        user.setRole("USER");
        return userRepo.saveOrUpdate(user);
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
    public void delete(Long id) {
        if (userRepo.findById(id).isEmpty())
            throw new IllegalArgumentException("No entity with such id");
        userRepo.delete(id);
        if (userRepo.findById(id).isPresent())
            throw new RuntimeException("Delete is not working");
    }
}
