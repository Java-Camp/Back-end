package com.jcf.persistence.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class OperationDTO {

    private Long id;
    private Instant dateTime;
    @NotEmpty
    private BigDecimal sum;
    @NotEmpty
    private BigDecimal accountId;
    @NotEmpty
    private BigDecimal operationTypeId;

    private BigDecimal operationId;
    @NotEmpty
    private BigDecimal categoryId;
}
