package com.jcf.service;

import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InfoForAccountService {
    List<Currency> getAllCurrency();
}
