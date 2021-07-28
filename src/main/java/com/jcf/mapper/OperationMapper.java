package com.jcf.mapper;

import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.dto.OperationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OperationMapper {

    OperationDTO toEntity(Operation operation);

    Operation toDto(OperationDTO operationDTO);
}
