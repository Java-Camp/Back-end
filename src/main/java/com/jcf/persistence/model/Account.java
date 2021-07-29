package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Account {

    @Id
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "language")
    private String language;

    @Column(name = "money")
    private double moneyBalance;

    @Column(name = "balance_type")
    private String balanceType;

    @Reference(name = "currency_id", fetchColumns = {"id", "name"})
    private Currency currency;

    @Reference(name = "account_type_id", fetchColumns = {"id", "name"})
    private AccountType accountType;

//    @MappedBy
//    private List<User> users;


}
