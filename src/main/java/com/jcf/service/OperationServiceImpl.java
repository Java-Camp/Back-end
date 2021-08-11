package com.jcf.service;

import com.jcf.exceptions.EntityNotFoundException;
import com.jcf.exceptions.FieldIsNullException;
import com.jcf.exceptions.LockedAccessException;
import com.jcf.exceptions.ServiceNotWorkingException;
import com.jcf.persistence.dto.OperationDTO;
import com.jcf.persistence.model.Operation;
import com.jcf.persistence.model.User;
import com.jcf.persistence.repository.OperationRepository;
import com.jcf.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@Async
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService{

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;


    @Override
    public Operation updateOperation(OperationDTO operationDTO){
        if(operationRepository.findById(operationDTO.getOperationId().longValue()).isEmpty())
            throw new EntityNotFoundException(operationDTO.getOperationId().longValue());

        Operation operation = new Operation();

        operation.setOperationId(operationDTO.getOperationId());
        operation.setSum(operationDTO.getSum());
        operation.setOperationTypeId(operationDTO.getOperationTypeId());
        operation.setDateTime(operationDTO.getDateTime());
        operation.setCategoryId(operationDTO.getCategoryId());

        return operationRepository.saveOrUpdate(operation);
    }

    @Override
    public Operation saveOperation(OperationDTO operationDTO) {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Operation operation = new Operation();
        User user = userRepository.findByEmail(userEmail);

        if (Objects.isNull(operationDTO.getDateTime())) {
            operation.setDateTime(Instant.now());
        } else {
            operation.setDateTime(operationDTO.getDateTime());
        }
        if (Objects.isNull(operationDTO.getAccountId())) {
            operation.setAccountId(new BigDecimal(user.getId()));
        } else {
            operation.setAccountId(operationDTO.getAccountId());
        }

        if(!user.getId().equals(operationDTO.getAccountId()))
            throw new LockedAccessException("You can't do anything with user " + userRepository.findById(operationDTO.getAccountId().longValue()).get().getEmail());
        if (Objects.isNull(operationDTO.getSum()))
            throw new FieldIsNullException("Sum");
        if (Objects.isNull(operationDTO.getOperationTypeId()))
            throw new FieldIsNullException("Operation Type");
        if (Objects.isNull(operationDTO.getCategoryId()))
            throw new FieldIsNullException("Category");

        operation.setSum(operationDTO.getSum());
        operation.setOperationTypeId(operationDTO.getOperationTypeId());
        operation.setOperationId(operationDTO.getOperationId());
        operation.setCategoryId(operationDTO.getCategoryId());
        operationRepository.saveOrUpdate(operation);
        Long id = operationRepository.getEntityID("ISEQ$$_105469");
        if(operationRepository.findById(id).isEmpty())
            throw new ServiceNotWorkingException("Save");
        operation.setId(id);
        return operation;
    }

    @Override
    public List<Operation> findAll(){
        return operationRepository.findAll();
    }

    @Override
    public void delete(Long id){
        if (operationRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id);
        operationRepository.delete(id);
        if (operationRepository.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }
}
