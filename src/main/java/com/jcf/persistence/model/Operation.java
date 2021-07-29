package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Operation {

    @Id
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column
    private double sum;

    @Reference(name = "account_id", fetchColumns = {"id", "alias", "language", "money", "balance_type"})
    private Account account;

    @Reference(name = "operation_type_id", fetchColumns = {"id", "name"})
    private OperationType operationType;

    @Reference(name = "category_id", fetchColumns = {"id", "name"})
    private OperationCategory operationCategory;

    @Reference(name = "operation_id", fetchColumns = {"id", "date_time", "sum"})
    private Operation operation;
}
