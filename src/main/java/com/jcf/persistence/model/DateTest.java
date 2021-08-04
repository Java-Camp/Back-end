package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "Date_Test")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class DateTest {
    @Id
    private Long id;

    @Column(name = "THE_DATE")
    private Instant dateTime;
}
