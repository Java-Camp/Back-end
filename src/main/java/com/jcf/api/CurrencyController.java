package com.jcf.api;

import com.jcf.persistence.model.Currency;
import com.jcf.persistence.repository.CurrencyRepository;
import com.jcf.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok().body(currencyService.getAllCurrency());
    }

    @GetMapping("/currencies/{id}")
    public Currency getCurrencyById(@PathVariable Long id) {
        return currencyService.getById(id);
    }

}
