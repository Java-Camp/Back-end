package com.jcf.security;

import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.GenericRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImp")
public class UserDetailsServiceImp implements UserDetailsService {

    private final GenericRepository genericRepository;

    @Autowired
    public UserDetailsServiceImp(GenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    /// ВОПРОС

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = genericRepository.findByEmail(s).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }
}