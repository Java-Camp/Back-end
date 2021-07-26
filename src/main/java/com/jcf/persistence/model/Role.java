package com.jcf.persistence.model;

import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;

@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    private Long id;

    @Column
    private String name;

}
