package com.jcf.persistence.model.dto;

import com.jcf.persistence.model.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class AccountDTO {

    @NotEmpty
    private Long id;
    @NotEmpty
    private String alias;
    @NotEmpty
    private String language;
    @NotEmpty
    private double moneyBalance;
    @NotEmpty
    private String balanceType;
    @NotEmpty
    private List<UserDTO> users;

}
