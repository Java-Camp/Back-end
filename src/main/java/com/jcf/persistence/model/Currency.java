package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CURRENCY")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString()
@AllArgsConstructor
public class Currency {

    @Id
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUNTRY")
    private String country;

}
