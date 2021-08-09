package com.jcf.api;


import com.jcf.service.CategoryService;
import com.jcf.service.CurrencyService;
import com.jcf.service.TypeOfAccountService;
import com.jcf.vo.AccountTypeVO;
import com.jcf.vo.CategoryVO;
import com.jcf.vo.CurrencyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edit")
@RequiredArgsConstructor
public class EditController {
    private final CurrencyService currencyService;
    private final TypeOfAccountService typeOfAccountService;
    private final CategoryService categoryService;

    @PostMapping("/delete/currencies/{id}")
    public void deleteCurrencyById(@PathVariable Long id) {
        currencyService.delete(id);
    }

    @PostMapping("/delete/typeOfAccount/{id}")
    public void deleteTypeOfAccountById(@PathVariable Long id) {
        typeOfAccountService.delete(id);
    }

    @PostMapping("/delete/categories/{id}")
    public void deleteCategoriesById(@PathVariable Long id) {
        categoryService.delete(id);
    }


    @PostMapping("/create/currencies")
    public void createCurrency(@PathVariable CurrencyVO currency) {
        currencyService.saveCurrency(currency);
    }

    @PostMapping("/create/categories")
    public void createCategory(@PathVariable CategoryVO categoryVO) {
        categoryService.createCategory(categoryVO);
    }

    @PostMapping("/create/typeOfAccount")
    public void createTypeOfAccount(@PathVariable AccountTypeVO accountTypeVO) {
        typeOfAccountService.createAccountType(accountTypeVO);
    }
}
