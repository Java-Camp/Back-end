package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Getter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
public class Account {

    @Id
    private Long id;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "MONEY")
    private double moneyBalance;

    @Column(name = "BALANCE_TYPE")
    private String balanceType;

    @Column(name = "ACCOUNT_TYPE_ID")
    private Long accountTypeId;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;
    //@Reference(ID, ...)
    private List<User> users;

    //
}
