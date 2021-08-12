package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "OPERATION")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Operation { // TODO CHANGE BIGDECIMAL TO LONG

    @Id
    private Long id;

    @Column(name = "DATE_TIME")
    private LocalDateTime dateTime;
    @Column(name = "SUM")
    private BigDecimal sum;
    @Column(name = "ACCOUNT_ID")
    private BigDecimal accountId;
    @Column(name = "OPERATION_TYPE_ID")
    private BigDecimal operationTypeId;
    @Column(name = "OPERATION_ID")
    private BigDecimal operationId;
    @Column(name = "CATEGORY_ID")
    private BigDecimal categoryId;
}
