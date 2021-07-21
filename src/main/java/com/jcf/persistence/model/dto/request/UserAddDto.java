package com.jcf.persistence.model.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")

public class UserAddDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
