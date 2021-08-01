package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime dateTime;
    @Column(name = "SUM")
    private BigDecimal sum;

    private Account account;

    private Operation operation;
    private OperationType operationType;
    private Category category;


}
