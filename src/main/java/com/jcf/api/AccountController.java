package com.jcf.api;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.service.AccountService;
import com.jcf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping("/accounts/save")
    public ResponseEntity<Account> saveAccount(@RequestBody AccountDto accountDto) {
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/accounts/save").toUriString());
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.created(uri).body(accountService.saveAccount(userEmail,accountDto));
    }

}
