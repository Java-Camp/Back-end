package com.jcf.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Builder
@EqualsAndHashCode(of = "id")
public class UserAccountDto {

    private BigDecimal ID;
    private String ALIAS;
    private BigDecimal MONEY;
    private String CURRENCY;
    private String ACCOUNT_TYPE;
}
