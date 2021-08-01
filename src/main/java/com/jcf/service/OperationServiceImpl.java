package com.jcf.service;

import com.jcf.persistence.dao.OperationDao;
import com.jcf.persistence.model.dto.OperationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
public class OperationServiceImpl implements OperationService{

    private final OperationDao operationDao;

    public OperationServiceImpl(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    @Override
    public OperationDTO findById(Long id) {
        return null;
    }

    @Override
    public Boolean saveOperation(String userEmail, OperationDTO operationDTOD) {
        return null;
    }

    @Override
    public List<OperationDTO> getAllOperations() {
        return null;
    }
}
