package com.jcf.api;

import com.jcf.persistence.dao.AccountDao;
import com.jcf.persistence.dto.AccountDto;
import com.jcf.persistence.dto.UserAccountDto;
import com.jcf.persistence.model.Account;
import com.jcf.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountDao accountDao;

/*    @GetMapping("")
    public ResponseEntity<List<UserAccountDto>> getAccounts() {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            accountDao.getAllUsers();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(accountDao.getAllUserAccounts(userEmail));
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<Integer> saveAccount(@RequestBody AccountDto accountDto) {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(accountDao.save(userEmail, accountDto));
    }*/

    @GetMapping("")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        accountService.delete(id);
        return ResponseEntity.ok("Entity was deleted");
    }
}
