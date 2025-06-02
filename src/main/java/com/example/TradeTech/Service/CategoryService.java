package com.example.TradeTech.Service;

import com.example.TradeTech.Exceptions.ResourceNotFoundException;
import com.example.TradeTech.Model.Category;
import com.example.TradeTech.Repository.CategoryRepository;
import com.example.TradeTech.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO dto) {
        try {
            Category category = new Category();
            category.setName(dto.name);
            return categoryRepository.save(category);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create category",e);
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch categories",e);
        }
    }

    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch category",e);
        }
    }

    public Category updateCategory(Long id, CategoryDTO dto) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
            category.setName(dto.name);
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category",e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            if(categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException("Category not found with id: " + id);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category",e);
        }
    }
}
