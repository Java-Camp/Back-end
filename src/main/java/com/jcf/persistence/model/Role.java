package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

@Entity
@Table(name = "ROLE")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Role {

    @Id
    private Long id;

    @Column
    private String name;

    @Reference(name = "user_id", fetchColumns = {"id", "first_name", "last_name", "email", "password"})
    private User user;

}
