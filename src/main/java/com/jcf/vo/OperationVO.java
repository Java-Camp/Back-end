package com.jcf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class OperationVO {

    private String type;

    private LocalDateTime date;
    private BigDecimal sum;
    private String category;
}
