package com.jcf.service;

import com.jcf.exceptions.EntityNotFoundException;
import com.jcf.exceptions.ServiceNotWorkingException;
import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.persistence.repository.AccountRepository;
import com.jcf.persistence.repository.UserAccountRepository;
import com.jcf.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Async
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public Account findById(Long id) {
        final Optional<Account> byId = accountRepository.findById(id);
        if (!byId.isPresent()) {
            throw new IllegalArgumentException("No such account!");
        }
        return byId.get();
    }

    @Override
    public Account saveAccount(String userEmail, AccountDto accountDto) {

//        final User user = userRepository.findByEmail(userEmail);
//
//        final Account account = accountRepository.saveOrUpdate(Account.builder()
//                .accountTypeId(accountDto.getAccountTypeId())
//                .alias(accountDto.getAlias())
//                .moneyBalance(1)
//                .balanceType("")
//                .language("")
//                .currencyId(accountDto.getCurrencyId())
//                .build());
//
//        userAccountRepository.saveOrUpdate(UserAccount
//                .builder()
//                .userId(user.getId())
//                .account_id(account.getId())
//                .build());

        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        List<Account> accountList = new ArrayList<>();

        for (UserAccount userAccount: userAccountRepository.findByUnique("userId", user.getId())){
            accountList.add(accountRepository.findById(userAccount.getAccount_id().longValue()).get());
        }

        return accountList;
    }

    @Override
    public void delete(Long id) {
        if (accountRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id);
        accountRepository.delete(id);
        // todo add delete UserAccount with account id...
        if (accountRepository.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }
}
