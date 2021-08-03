package com.jcf.mapper;


import com.jcf.persistence.model.Operation;

import com.jcf.persistence.dto.OperationDTO;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper {

    private final ModelMapper modelMapper;

    public OperationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;


    }
    public OperationDTO convertToDto(Operation entity){

        return modelMapper.map(entity, OperationDTO.class);
    }

    public Operation convertToEntity(OperationDTO dto){

        return modelMapper.map(dto, Operation.class);
    }

}
