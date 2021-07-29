package com.jcf.persistence.model.dto;

import com.jcf.orm.annotation.Column;
import com.jcf.persistence.model.Account;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class OperationDTO {

    @NotEmpty
    private Long id;
    @NotEmpty
    private LocalDateTime dateTime;

    private double sum;
    @NotEmpty
    private AccountDTO account;
}
