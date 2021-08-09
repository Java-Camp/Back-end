package com.jcf.service;

import com.jcf.persistence.model.Currency;
import com.jcf.persistence.model.User;
import com.jcf.vo.CurrencyVO;
import com.jcf.vo.UserVO;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAllCurrency();
    Currency getById(Long id);
    void delete(Long id);
    Currency saveCurrency(CurrencyVO currencyVO);

}
