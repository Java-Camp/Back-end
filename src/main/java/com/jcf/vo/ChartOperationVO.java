package com.jcf.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ChartOperationVO {

    private LocalDate date;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal transfer;

}
