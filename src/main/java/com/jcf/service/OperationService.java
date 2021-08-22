package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;


import java.util.List;

public interface OperationService {
    Operation updateOperation(OperationDTO operationDTO);
    Operation saveOperation(OperationDTO operationDTOD);
    List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter);
    void delete(Long id);

}
