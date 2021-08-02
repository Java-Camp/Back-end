package com.jcf.service;

import com.jcf.persistence.model.Currency;
import com.jcf.persistence.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repository;

    @Override
    public List<Currency> getAllCurrency() {
        return repository.findAllCurrency();
    }

}
