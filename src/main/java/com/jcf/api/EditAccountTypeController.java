package com.jcf.api;

import com.jcf.service.TypeOfAccountService;
import com.jcf.vo.AccountTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/edit/typeOfAccount")
@RequiredArgsConstructor
public class EditAccountTypeController {
    private final TypeOfAccountService typeOfAccountService;

    @DeleteMapping("/{id}")
    public void deleteTypeOfAccountById(@PathVariable Long id) {
        typeOfAccountService.delete(id);
    }

    @PostMapping
    public void createTypeOfAccount(@PathVariable AccountTypeVO accountTypeVO) {
        typeOfAccountService.createAccountType(accountTypeVO);
    }
}
