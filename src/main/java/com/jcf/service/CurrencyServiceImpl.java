package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Currency;
import com.jcf.persistence.repository.CurrencyRepository;
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
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repository;

    @Override
    public List<Currency> getAllCurrency() {
        return repository.findAllCurrency();
    }

    @Override
    public Currency getById(Long id) {
        final Optional<Currency> byId = repository.findById(id);
        if (!byId.isPresent()) {
            throw new IllegalArgumentException("No such currency!");
        }
        return byId.get();
    }

}
