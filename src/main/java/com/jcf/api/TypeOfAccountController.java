package com.jcf.api;

import com.jcf.persistence.model.AccountType;
import com.jcf.persistence.model.Currency;
import com.jcf.service.TypeOfAccountService;
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
public class TypeOfAccountController {
    private final TypeOfAccountService typeOfAccountService;

    @GetMapping("/typeOfAccount")
    public ResponseEntity<List<AccountType>> getCurrencies() {
        return ResponseEntity.ok().body(typeOfAccountService.getAllAccountType());
    }

    @GetMapping("/typeOfAccount/{id}")
    public AccountType getCurrencyById(@PathVariable Long id) {
        return typeOfAccountService.findById(id);
    }

}