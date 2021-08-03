package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;

public interface OperationService {


    Boolean saveOperation(String userEmail, OperationDTO operationDTOD);

}
