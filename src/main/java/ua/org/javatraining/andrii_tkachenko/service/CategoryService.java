package ua.org.javatraining.andrii_tkachenko.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    public Category findById(Integer id) {
        return categoryRepository.findOne(id);
    }

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public List<Category> findAllWithSubcategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        categories.forEach(category -> Hibernate.initialize(category.getSubCategories()));
        return categories;
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }
}