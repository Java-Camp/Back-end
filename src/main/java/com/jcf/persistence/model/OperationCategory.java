package com.jcf.persistence.model;

import com.jcf.orm.annotation.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CATEGORY")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class OperationCategory {
    @Id
    private Long id;

    @Column
    private String name;

    @MappedBy(mappedBy = "operationCategory", entityClass = Operation.class)
    List<Operation> operations;
}
