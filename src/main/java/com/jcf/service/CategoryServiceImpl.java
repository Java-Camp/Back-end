package com.jcf.service;

import com.jcf.persistence.model.Account;
import com.jcf.persistence.model.Category;
import com.jcf.persistence.model.Currency;
import com.jcf.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAllCategory();
    }

    @Override
    public Category getById(Long id) {
        final Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent()) {
            throw new IllegalArgumentException("No such currency!");
        }
        return byId.get();
    }
}
