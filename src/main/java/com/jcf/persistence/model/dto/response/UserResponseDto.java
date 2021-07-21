package com.jcf.persistence.model.dto.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
