package com.jcf.persistence.dto;



import lombok.*;

import javax.validation.constraints.NotEmpty;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO {

    @NotEmpty
    private Long id;
    @NotEmpty
    private String name;

}
