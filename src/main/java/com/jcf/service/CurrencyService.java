package com.jcf.service;

import com.jcf.persistence.model.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAllCurrency();
}