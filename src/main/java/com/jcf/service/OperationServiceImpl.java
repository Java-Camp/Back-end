package com.jcf.service;
import com.jcf.persistence.dao.OperationDao;
import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.repository.OperationRepository;
import com.jcf.vo.FilteredOperationDto;
import com.jcf.vo.OperationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.TIMESTAMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;


@Service
@Async
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService{

    private final OperationDao operationDao;
    private final OperationRepository operationRepository;



    public OperationServiceImpl(OperationDao operationDao, OperationRepository operationRepository) {
        this.operationDao = operationDao;
        this.operationRepository = operationRepository;
    }


    @Override
    public Operation updateOperation(OperationDTO operationDTO) {
        return null;
    }

    @Override
    public Operation saveOperation(OperationDTO operationDTO) {
        Operation operation = new Operation();
        operation.setDateTime(Instant.now());
        operation.setSum(operationDTO.getSum());
        operation.setAccountId(operationDTO.getAccountId());
        operation.setOperationTypeId(operationDTO.getOperationTypeId());
        operation.setOperationId(operationDTO.getOperationId());
        operation.setCategoryId(operationDTO.getCategoryId());

        return operationRepository.saveOrUpdate(operation);
    }

    @Override
    public List<OperationVO> findOperationsByFilter(Long accountId, FilteredOperationDto filter) {
        return operationDao.getFilteredOperation(accountId, filter);
    }

    @Override
    public void delete(Long id) {

    }


}
