package com.jcf.service;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.dto.OperationDTO;

import java.util.List;

public interface OperationService {

    OperationDTO findById(Long id);
    Boolean saveOperation(String userEmail, OperationDTO operationDTOD);
    List<OperationDTO> getAllOperations();
}
