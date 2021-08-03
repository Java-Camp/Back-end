package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Table(name = "OPERATION")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Operation {

    @Id
    private Long id;

    @Column(name = "DATE_TIME")
    private Date dateTime;
    @Column(name = "SUM")
    private BigDecimal sum;
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    @Column(name = "OPERATION_TYPE_ID")
    private Long operationTypeId;
    @Column(name = "OPERATION_ID")
    private Long operationId;
    @Column(name = "CATEGORY_ID")
    private Long categoryId;


}
