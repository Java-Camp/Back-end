package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_ACCOUNT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Model(tableName = "USER_ACCOUNT", primaryKey = "ID")
public class UserAccount {

    @Id
    private Integer id;

    @Column(name = "USER_ID", nullable = false)
    private BigDecimal userId;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private BigDecimal account_id;

    @ManyToOne(joinColumn = "ACCOUNT_ID")
    private Account account;

    @ManyToOne(joinColumn = "USER_ID")
    private User user;

}
