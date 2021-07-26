package com.jcf.persistence.model.entity;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
public class User {

    @Id
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "email", nullable= false, unique = true)
    private String email;

    @Column(name ="password")
    private String password;

    @Column(name ="role")
    private Role role;


}
