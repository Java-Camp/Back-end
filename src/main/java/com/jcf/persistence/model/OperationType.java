package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "OPERATION_TYPE")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class OperationType {

    @Id
    private Long id;

    @Column
    private String name;

    @MappedBy(mappedBy = "operationType", entityClass = Operation.class)
    List<Operation> operations;
}
