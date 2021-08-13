package com.jcf.persistence.model;


import com.jcf.orm.annotation.*;
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
@Model(tableName = "ACCOUNT_TYPE", primaryKey = "ID")
public class AccountType {
    @Id
    private Long id;
    @Column(name = "NAME")
    private String name;
}
