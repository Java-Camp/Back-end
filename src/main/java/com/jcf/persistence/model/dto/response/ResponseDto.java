package com.jcf.persistence.model.dto.response;

import com.jcf.persistence.model.dto.ErrorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
public class ResponseDto <T> {

    private T data;
    private List<ErrorDto> errors;

    public ResponseDto(T data) {
        this.data = data;
        errors = new ArrayList<>();
    }

}
