package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Model(tableName = "ACCOUNT", primaryKey = "ID")
public class Account {

    @Id
    private Integer id;

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

    @ManyToOne(joinColumn = "CURRENCY_ID")
    private Currency currency;

    @OneToMany(mappedBy = "account")
    public Set<UserAccount> userAccounts;


}
