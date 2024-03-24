package com.example.ecommerce.Service;

import com.example.ecommerce.Exception.CategoryNotFoundException;
import com.example.ecommerce.Model.Category;
import com.example.ecommerce.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category updatedCategory, Integer id) {
        Category category = getCategoryById(id);

        category.setName(updatedCategory.getName());

        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
    }

}
