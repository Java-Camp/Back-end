package com.jcf.persistence.model.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Date;


@NoArgsConstructor
@Getter
@Setter

@ToString
public class OperationDTO {


    @NotEmpty
    private Date dateTime;
    @NotEmpty
    private BigDecimal sum;
    @NotEmpty
    private Long accountId;
    @NotEmpty
    private Long operationTypeId;

    private Long operationId;
    @NotEmpty
    private Long categoryId;
}
