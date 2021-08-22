package com.jcf.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;


@Data
public class FilteredOperationDto {


    private LocalDateTime firstDate;
    private LocalDateTime secondDate;
    private String type;
    private String category;

}
