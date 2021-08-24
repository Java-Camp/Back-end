package com.jcf.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateFilteredOperationVO {

    private LocalDateTime firstDate;
    private LocalDateTime secondDate;

}
