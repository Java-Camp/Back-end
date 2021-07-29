package com.jcf.mapper;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.dto.AccountDTO;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class AccountMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public AccountDTO convertToDto(Account entity) {
        return modelMapper.map(entity, AccountDTO.class);
    }

    public Account convertToEntity(AccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }

}
