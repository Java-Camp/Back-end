package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.AccountType;

import java.util.List;

public interface TypeOfAccountService {
    AccountType findById(Long id);
    List<AccountType> getAllAccountType();
}
