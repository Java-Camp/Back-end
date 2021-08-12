package com.jcf.api;

import com.jcf.persistence.model.Currency;
import com.jcf.service.CurrencyService;
import com.jcf.vo.CurrencyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok().body(currencyService.getAllCurrency());
    }

    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable Long id) {
        return currencyService.getById(id);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/edit/{id}")
    public void deleteCurrencyById(@PathVariable Long id) {
        currencyService.delete(id);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public void addCurrency(@PathVariable CurrencyVO currency) {
        currencyService.saveCurrency(currency);
    }
}
