package com.jcf.api;

import com.jcf.persistence.model.Category;
import com.jcf.service.CategoryService;
import com.jcf.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public void addCategory(@PathVariable CategoryVO categoryVO) {
        categoryService.createCategory(categoryVO);
    }


    @DeleteMapping("/{id}")
    public void deleteCategoriesById(@PathVariable Long id) {
        categoryService.delete(id);
    }


}