package com.jcf.config;

import com.jcf.security.Permission;
import com.jcf.security.JwtConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)


public class SecurityConfig extends WebSecurityConfigurerAdapter {
   private final JwtConfigure jwtConfigure;

    public SecurityConfig(JwtConfigure jwtConfigure) {
        this.jwtConfigure = jwtConfigure;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // after create controllers USING preAuthorize
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/login/**").hasAuthority(Permission.USERS_READ.getPermission())
                .antMatchers(HttpMethod.DELETE, "/login/**").hasAuthority(Permission.USERS_WRITE.getPermission())
                // delete this ^-^
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigure);

    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super .authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}

