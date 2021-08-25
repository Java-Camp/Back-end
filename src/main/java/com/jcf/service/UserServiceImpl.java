package com.jcf.service;

import com.jcf.exceptions.EntityNotFoundException;
import com.jcf.exceptions.FieldIsNullException;
import com.jcf.exceptions.ServiceNotWorkingException;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.persistence.repository.UserAccountRepository;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Async
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new EntityNotFoundException(username);
        else
            log.error("User found: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User updateUser(UserVO vo){
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(userEmail);
        if(!Objects.isNull(vo.getFirstName()))
            user.setFirstName(vo.getFirstName());
        if(!Objects.isNull(vo.getLastName()))
            user.setLastName(vo.getLastName());
        if(!Objects.isNull(vo.getEmail()))
            user.setEmail(vo.getEmail());
        if(!Objects.isNull(vo.getPassword()))
            user.setPassword(passwordEncoder.encode(vo.getPassword()));
        return userRepo.saveOrUpdate(user);
    }

    @Override
    public User saveUser(UserVO vo) {
        log.info("Saving new user to database");
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        User user = new User();
        if (Objects.isNull(vo.getFirstName()))
            throw new FieldIsNullException("firstName");
        if (Objects.isNull(vo.getLastName()))
            throw new FieldIsNullException("lastName");
        if (Objects.isNull(vo.getEmail()))
            throw new FieldIsNullException("email");
        if (Objects.isNull(vo.getPassword()))
            throw new FieldIsNullException("password");

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
    public User getUserById(String userEmail) {
        User user = userRepo.findByEmail(userEmail);
        final Optional<User> byId = userRepo.findById(user.getId());
        if (byId.isEmpty())
            throw new EntityNotFoundException(user.getId());
        return byId.get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Getting all users from database");
        return null;
    }

    @Override
    public void delete(Long id) {

        for(UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", id))
            userAccountRepository.delete(userAccount.getId());

        if (userRepo.findById(id).isEmpty())
            throw new EntityNotFoundException(id);
        userRepo.delete(id);
        if (userRepo.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }

    public void findAll(){
        userRepo.findAll();
    }
}
