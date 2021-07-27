package com.jcf;

import com.jcf.persistence.model.User;
import com.jcf.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.jcf")
public class EntryPoint{

    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveUser(new User(null,"Julia","julia30","user","user","ADMIN"));
        };
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
}
