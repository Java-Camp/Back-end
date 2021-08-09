package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import com.jcf.vo.CategoryVO;
import com.jcf.vo.CurrencyVO;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getById(Long id);
    void delete(Long id);
    Category createCategory(CategoryVO categoryVO);

}
