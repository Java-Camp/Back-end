package com.jcf.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {

    private Long id;
    private String alias;
    private String language;
    private BigDecimal moneyBalance;
    private String balanceType;
    private BigDecimal accountTypeId;
    private BigDecimal currencyId;

}
