package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
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

//    //@Reference(ID, ...)
//    private List<Role> roles;
//
//    //@Reference(ID, ...)
//    private List<Account> accounts;


//    @Column(name ="role")
//    private Role role;


}
