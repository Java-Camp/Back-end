package com.jcf.service;

import com.jcf.exceptions.EntityNotFoundException;
import com.jcf.exceptions.FieldIsNullException;
import com.jcf.exceptions.LockedAccessException;
import com.jcf.exceptions.ServiceNotWorkingException;
import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.dto.OperationDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private boolean isControl(AccountDto accountDto, User user){
        for (UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", user.getId())){
            if (accountDto.getId().equals(userAccount.getAccount_id().longValue()))
                return true;
        }
        return false;
    }

    private boolean isControl(Account account, User user){
        for (UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", user.getId())){
            if (account.getId().equals(userAccount.getAccount_id().longValue()))
                return true;
        }
        return false;
    }

    @Override
    public Account findById(Long id) {
        Optional<Account> byId = accountRepository.findById(id);
        if (byId.isEmpty())
            throw new EntityNotFoundException(id);
        return byId.get();
    }

    @Override
    public Account updateAccount(AccountDto accountDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        Account account = accountRepository.findById(accountDto.getId()).get();

        if(!isControl(accountDto, user))
            throw new LockedAccessException("You can't do anything with account " + account.getAlias());

        if(!Objects.isNull(accountDto.getAlias()))
            account.setAlias(accountDto.getAlias());
        if(!Objects.isNull(accountDto.getLanguage()))
            account.setLanguage(accountDto.getLanguage());
        if(!Objects.isNull(accountDto.getMoneyBalance()))
            account.setMoneyBalance(accountDto.getMoneyBalance());
        if(!Objects.isNull(accountDto.getBalanceType()))
            account.setBalanceType(accountDto.getBalanceType());
        if(!Objects.isNull(accountDto.getAccountTypeId()))
            account.setAccountTypeId(accountDto.getAccountTypeId());
        if(!Objects.isNull(accountDto.getCurrencyId()))
            account.setCurrencyId(accountDto.getCurrencyId());

        return accountRepository.saveOrUpdate(account);
    }

    @Override
    public Account count(AccountDto accountDto) {
        return null;
    }

    @Override
    public Account saveAccount(AccountDto accountDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        Account account = new Account();

        if(Objects.isNull(accountDto.getAlias()))
            throw new FieldIsNullException("Alias");
        if(Objects.isNull(accountDto.getLanguage()))
            throw new FieldIsNullException("Language");
        if(Objects.isNull(accountDto.getMoneyBalance()))
            throw new FieldIsNullException("Money Balance");
        if(Objects.isNull(accountDto.getBalanceType()))
            throw new FieldIsNullException("Balance Type");
        if(Objects.isNull(accountDto.getAccountTypeId()))
            throw new FieldIsNullException("Account Type");
        if(Objects.isNull(accountDto.getCurrencyId()))
            throw new FieldIsNullException("Currency");

        account.setAlias(accountDto.getAlias());
        account.setLanguage(accountDto.getLanguage());
        account.setMoneyBalance(accountDto.getMoneyBalance());
        account.setBalanceType(accountDto.getBalanceType());
        account.setAccountTypeId(accountDto.getAccountTypeId());
        account.setCurrencyId(accountDto.getCurrencyId());

        account = accountRepository.saveOrUpdate(account);
        // todo add changes to User_Account table


        userAccountRepository.saveOrUpdate(UserAccount
                .builder()
                .userId(BigDecimal.valueOf(user.getId()))
                .account_id(BigDecimal.valueOf(account.getId()))
                .build());

        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);
        List<Account> accountList = new ArrayList<>();

        for (UserAccount userAccount: userAccountRepository.findByUnique("USER_ID", user.getId())){
            accountList.add(accountRepository.findById(userAccount.getAccount_id().longValue()).get());
        }

        return accountList;
    }

    @Override
    public void delete(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username);

        if(!isControl(accountRepository.findById(id).get(), user))
            throw new LockedAccessException("You can't do anything with account " + accountRepository.findById(id).get().getAlias());

        for(UserAccount userAccount: userAccountRepository.findByUnique("ACCOUNT_ID", id))
            userAccountRepository.delete(userAccount.getId());

        if (accountRepository.findById(id).isEmpty())
            throw new EntityNotFoundException(id);

        accountRepository.delete(id);

        if (accountRepository.findById(id).isPresent())
            throw new ServiceNotWorkingException("Delete");
    }
}
