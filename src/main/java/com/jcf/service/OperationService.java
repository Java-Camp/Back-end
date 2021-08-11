package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;

import java.util.List;

public interface OperationService {
    Operation updateOperation(OperationDTO operationDTO);
    Operation saveOperation(OperationDTO operationDTOD);
    List findAll();
    void delete(Long id);

}
