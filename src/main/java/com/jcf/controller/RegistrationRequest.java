package com.jcf.controller;

import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class RegistrationRequest {
    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
