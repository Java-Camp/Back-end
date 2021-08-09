package com.jcf.service;

import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Async
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService{

    private final OperationRepository operationRepository;

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
    public List<Operation> findAll(){
        return operationRepository.findAll();
    }
}
