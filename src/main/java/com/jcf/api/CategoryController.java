package com.jcf.api;

import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import com.jcf.service.CategoryService;
import com.jcf.service.CurrencyService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

}
