package com.jcf.persistence.dto;

import com.jcf.validation.annotations.ValidEmail;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
@AllArgsConstructor

public class UserDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @ValidEmail
    private String email;

    @NotEmpty
    private String role;
}
