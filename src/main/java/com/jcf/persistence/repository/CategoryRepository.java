package com.jcf.persistence.repository;

import com.jcf.orm.core.EntityMapper;
import com.jcf.orm.core.Session;
import com.jcf.persistence.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository extends GenericRepository<Category, Long> {

    public CategoryRepository(Session<Category, Long> session) {
        super(session, Category.class);
    }

    public List<Category> findAllCategory() {
        return session.findAll(new EntityMapper(Category.class));
    }
}
