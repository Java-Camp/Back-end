package com.jcf.api;

import com.jcf.persistence.model.AccountType;
import com.jcf.service.TypeOfAccountService;
import com.jcf.vo.AccountTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typeOfAccount")
@RequiredArgsConstructor
public class TypeOfAccountController {
    private final TypeOfAccountService typeOfAccountService;

    @GetMapping
    public ResponseEntity<List<AccountType>> getTypeOfAccount() {
        return ResponseEntity.ok().body(typeOfAccountService.getAllAccountType());
    }

    @GetMapping("/{id}")
    public AccountType getTypeOfAccountById(@PathVariable Long id) {
        return typeOfAccountService.findById(id);
    }

    @DeleteMapping("/edit/{id}")
    public void deleteTypeOfAccountById(@PathVariable Long id) {
        typeOfAccountService.delete(id);
    }

    @PostMapping
    public void createTypeOfAccount(@PathVariable AccountTypeVO accountTypeVO) {
        typeOfAccountService.createAccountType(accountTypeVO);
    }
}
