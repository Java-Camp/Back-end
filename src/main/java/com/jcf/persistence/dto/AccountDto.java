package com.jcf.persistence.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {

    private Long accountTypeId;
    private String alias;
    private Long currencyId;

}
