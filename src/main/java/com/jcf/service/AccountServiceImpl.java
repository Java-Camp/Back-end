package com.jcf.service;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.User;
import com.jcf.persistence.model.UserAccount;
import com.jcf.persistence.repository.AccountRepository;
import com.jcf.persistence.repository.UserAccountRepository;
import com.jcf.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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

        final User user = userRepository.findByEmail(userEmail);

        final Account account = accountRepository.saveOrUpdate(Account.builder()
                .accountTypeId(accountDto.getAccountTypeId())
                .alias(accountDto.getAlias())
                .moneyBalance(1)
                .balanceType("")
                .language("")
                .currencyId(accountDto.getCurrencyId())
                .build());

        userAccountRepository.saveOrUpdate(UserAccount
                .builder()
                .userId(user.getId())
                .account_id(account.getId())
                .build());

        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);

        //List<Account>  accountList = accountRepository.findById(userAccountRepository.findById(user.getId()));
        return null;
    }
}
