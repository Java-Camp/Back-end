package com.jcf.api;


import com.jcf.service.CurrencyService;
import com.jcf.service.TypeOfAccountService;
import com.jcf.vo.AccountTypeVO;
import com.jcf.vo.CurrencyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/edit/currencies")
@RequiredArgsConstructor
public class EditCurrencyController {
    private final CurrencyService currencyService;

    @DeleteMapping("/{id}")
    public void deleteCurrencyById(@PathVariable Long id) {
        currencyService.delete(id);
    }


    @PostMapping
    public void createCurrency(@PathVariable CurrencyVO currency) {
        currencyService.saveCurrency(currency);
    }
}
