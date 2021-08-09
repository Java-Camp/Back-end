package com.jcf.api;


import com.jcf.service.CategoryService;
import com.jcf.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/edit/categories")
@RequiredArgsConstructor
public class EditCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public void postCategory(@PathVariable CategoryVO categoryVO) {
        categoryService.createCategory(categoryVO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoriesById(@PathVariable Long id) {
        categoryService.delete(id);
    }


}
