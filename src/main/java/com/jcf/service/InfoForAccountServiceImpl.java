package com.jcf.service;

import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;

import com.jcf.persistence.repository.CategoryRepository;
import com.jcf.persistence.repository.InfoForAccountRepository;
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
public class InfoForAccountServiceImpl implements InfoForAccountService,TypeOfAccountService,CategoryService{
    private final InfoForAccountRepository repository;
    private final TypeOfAccountRepository typeOfAccountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Currency> getAllCurrency() {
        return repository.findAllCurrency();
    }

    @Override
    public List<AccountType> getAllAccountType() {
        return typeOfAccountRepository.findAllTypeOfAccount();
    }


    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllCategory();
    }
}
