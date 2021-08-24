package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "OPERATION_TYPE_ID")
    private BigDecimal OperationType;
}
