package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Currency;
import com.jcf.persistence.repository.TypeOfAccountRepository;
import com.jcf.vo.AccountTypeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Async
public class TypeOfAccountServiceImpl implements TypeOfAccountService{

private final TypeOfAccountRepository typeOfAccountRepository;
    @Override
    public List<AccountType> getAllAccountType() {
        return typeOfAccountRepository.findAllTypeOfAccount();
    }

    @Override
    public void delete(Long id) {
        typeOfAccountRepository.delete(id);
    }

    @Override
    public AccountType createAccountType(AccountTypeVO accountTypeVO) {
        log.info("Saving new account type to database");
        AccountType accountType = new AccountType();
        accountType.setName(accountTypeVO.getName());
        return typeOfAccountRepository.saveOrUpdate(accountType);
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
