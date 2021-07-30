package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "USER_ACCOUNT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private BigDecimal userId;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private BigDecimal account_id;
}
