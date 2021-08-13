package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
@AllArgsConstructor
@Model(tableName = "USER", primaryKey = "ID")
public class User {

    @Id
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 150)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name ="PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @OneToMany(mappedBy = "user")
    private Set<UserAccount> userAccounts;

    // TODO: 13.08.2021
    //  3. solve problem with parse Integer ids in orm or security
    //  4. get Set<> of account from userAccounts


//    @OneToMany(mappedBy = "user")
//    private Set<Account> accounts;

//    @ManyToOne(joinColumn = "ACCOUNT_ID")
//    private Set<Account> accounts;

//    @ManyToOne(joinColumn = "ID")
//    private UserAccount userAccount;

//    //@Reference(ID, ...)
//    private List<Role> roles;

//    //@Reference(ID, ...)
//    private List<Account> accounts;


//    @Column(name ="role")
//    private Role role;





}
