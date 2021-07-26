package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
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

    //@Reference(ID, ...)

    private Account account;
}
