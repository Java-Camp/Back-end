package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.vo.*;


import java.util.List;

public interface OperationService {
    Operation updateOperation(OperationDTO operationDTO);
    Operation saveOperation(OperationDTO operationDTOD);
    List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter);
    List<ChartOperationVO> getOperationsTypeSumByDate(Long accountId, DateFilteredOperationVO filter);
    List<SpecialOperationVo> getOperationsByCurrentDate(Long accountID);
    void delete(Long id);

}
