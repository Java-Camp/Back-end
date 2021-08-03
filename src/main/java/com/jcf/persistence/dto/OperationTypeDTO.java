package com.jcf.persistence.dto;



import lombok.*;

import javax.validation.constraints.NotEmpty;



@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class OperationTypeDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;
}
