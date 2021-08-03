package com.jcf.service;

import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.repository.TypeOfAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TypeOfAccountServiceImpl implements TypeOfAccountService{

private final TypeOfAccountRepository typeOfAccountRepository;
    @Override
    public List<AccountType> getAllAccountType() {
        return typeOfAccountRepository.findAllTypeOfAccount();
    }
}
