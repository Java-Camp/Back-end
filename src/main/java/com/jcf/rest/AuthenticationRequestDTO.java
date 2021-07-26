package com.jcf.rest;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String email;
    private String password;
    private Long id;

}
