package com.jcf.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class RequestOpFilterDto {
    private String firstDate;
    private String secondDate;
    private String type;
    private String category;
}
