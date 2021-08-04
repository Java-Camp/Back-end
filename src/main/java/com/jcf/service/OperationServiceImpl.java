package com.jcf.service;

import com.jcf.mapper.OperationMapper;
import com.jcf.persistence.dao.OperationDao;
import com.jcf.persistence.dto.OperationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@Async
public class OperationServiceImpl implements OperationService{

private final OperationDao operationDao;
private final OperationMapper mapper;

@Autowired
    public OperationServiceImpl(OperationDao operationDao, OperationMapper mapper) {
        this.operationDao = operationDao;
        this.mapper = mapper;
    }


    @Override
    public Boolean saveOperation(String userEmail, OperationDTO operationDTO) {
        return operationDao.saveOperation(userEmail, mapper.convertToEntity(operationDTO) );
    }



}
