package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.repository.TypeOfAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Override
    public AccountType findById(Long id) {
        final Optional<AccountType> byId = typeOfAccountRepository.findById(id);
        if (!byId.isPresent()) {
            throw new IllegalArgumentException("No such type of account!");
        }
        return byId.get();
    }
}
