package com.jcf.api;


import com.jcf.service.CategoryService;
import com.jcf.service.CurrencyService;
import com.jcf.service.TypeOfAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edit")
@RequiredArgsConstructor
public class EditController {
    private final CurrencyService currencyService;
    private final TypeOfAccountService typeOfAccountService;
    private final CategoryService categoryService;

    @GetMapping("/delete/currencies/{id}")
    public void deleteCurrencyById(@PathVariable Long id) {
        currencyService.delete(id);
    }

    @GetMapping("/delete/typeOfAccount/{id}")
    public void deleteTypeOfAccountById(@PathVariable Long id) {
        typeOfAccountService.delete(id);
    }

    @GetMapping("/delete/categories/{id}")
    public void deleteCategoriesById(@PathVariable Long id) {
        categoryService.delete(id);
    }


}
