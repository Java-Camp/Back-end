package com.jcf.persistence.model;


import com.jcf.orm.annotation.Column;
import com.jcf.orm.annotation.Entity;
import com.jcf.orm.annotation.Id;
import com.jcf.orm.annotation.Table;
import lombok.*;

@Entity
@Table(name = "ACCOUNT_TYPE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    @Id
    private Long id;
    @Column(name = "NAME")
    private String name;
}
