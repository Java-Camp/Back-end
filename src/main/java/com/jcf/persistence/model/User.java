package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "roles")
@AllArgsConstructor
public class User {

    @Id
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name ="password")
    private String password;

    @Column
    private String role;

    @MappedBy(mappedBy = "user", entityClass = Role.class)
    private List<Role> roles;

//
//    //@MappedBy
//    private List<Account> accounts;

}
