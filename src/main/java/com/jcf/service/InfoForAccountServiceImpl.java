package com.jcf.service;

import com.jcf.persistence.model.Currency;

import com.jcf.persistence.repository.InfoForAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InfoForAccountServiceImpl implements InfoForAccountService{
    private final InfoForAccountRepository repository;

    @Override
    public List<Currency> getAllCurrency() {
        return repository.findAllCurrency();
    }
}
