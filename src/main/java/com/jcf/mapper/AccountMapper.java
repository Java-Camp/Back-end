package com.jcf.mapper;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    AccountDTO toEntity(Account account);

    Account toDto(AccountDTO accountDTO);

}
