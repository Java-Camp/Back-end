package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
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

    //@Reference(ID, ...)
    private List<User> users;

    //
}
