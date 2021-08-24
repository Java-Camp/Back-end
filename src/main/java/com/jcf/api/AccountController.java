package com.jcf.api;

import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("")
    public ResponseEntity<Account> saveAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.saveAccount(accountDto));
    }

    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateAccount(accountDto));
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        accountService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }

    @PutMapping("/UpdateAll")
    public ResponseEntity<String> UpdateAll(){
        accountService.updateAll();
        return ResponseEntity.ok("All accounts were updated");
    }
}