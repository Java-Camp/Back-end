package com.jcf.persistence.model.dto;


import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Id;
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
