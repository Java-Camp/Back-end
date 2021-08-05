package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private Long id;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "MONEY")
    private BigDecimal moneyBalance;

    @Column(name = "BALANCE_TYPE")
    private String balanceType;

    @Column(name = "ACCOUNT_TYPE_ID")
    private BigDecimal accountTypeId;

    @Column(name = "CURRENCY_ID")
    private BigDecimal currencyId;

    @ManyToMany
    @JoinTable(
            name = "USER_ACCOUNT",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> users;
}
