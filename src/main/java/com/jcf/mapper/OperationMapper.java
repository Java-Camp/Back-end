package com.jcf.mapper;


import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.dto.AccountDTO;
import com.jcf.persistence.model.dto.OperationDTO;
import com.jcf.persistence.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper {

    private final ModelMapper modelMapper;

    public OperationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Account.class, AccountDTO.class);
        modelMapper.createTypeMap(AccountDTO.class, Account.class);
    }
    public OperationDTO convertToDto(Operation entity){
        return modelMapper.map(entity, OperationDTO.class);
    }

    public Operation convertToEntity(OperationDTO dto){
        return modelMapper.map(dto, Operation.class);
    }

}
