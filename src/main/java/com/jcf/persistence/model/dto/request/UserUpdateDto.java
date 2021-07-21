package com.jcf.persistence.model.dto.request;


import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Id;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@EqualsAndHashCode(of = "id", callSuper = false)

public class UserUpdateDto extends UserAddDto{

    private Long id;

}
