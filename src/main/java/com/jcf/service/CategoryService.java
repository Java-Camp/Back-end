package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getById(Long id);
    void delete(Long id);
}
