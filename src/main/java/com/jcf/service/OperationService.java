package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import com.jcf.vo.SpecialOperationVo;


import java.time.LocalDateTime;
import java.util.List;

public interface OperationService {
    Operation updateOperation(OperationDTO operationDTO);
    Operation saveOperation(OperationDTO operationDTOD);
    List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter);
    List<SpecialOperationVo> getOperationsByCurrentDate(Long accountID);
    void delete(Long id);

}
