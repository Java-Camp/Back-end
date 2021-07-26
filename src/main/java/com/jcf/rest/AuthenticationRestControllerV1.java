package com.jcf.rest;

import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.UserRepository;
import com.jcf.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationRestControllerV1{

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request){
        try {
            String email = request.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Optional<User> user = userRepository.findById(request.getId());
            String token = jwtTokenProvider.createToken(request.getEmail(), String.valueOf(user.get().getRole()));
            Map<Object,Object> response = new HashMap<>();
            response.put("email",request.getEmail());
            response.put("token",token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return  new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }



}

@PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    securityContextLogoutHandler.logout(request,response,null);
}
}
