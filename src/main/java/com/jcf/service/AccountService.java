package com.jcf.service;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;

import java.util.List;

public interface AccountService {

    Account findById(Long id);
    Account saveAccount(AccountDto accountDto);
    List<Account> getAllAccounts();
    void delete(Long id);
    Account updateAccount(AccountDto accountDto);
    void updateAll();
}
