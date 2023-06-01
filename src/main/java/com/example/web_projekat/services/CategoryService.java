package com.example.web_projekat.services;

import com.example.web_projekat.entities.Category;
import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.category.CategoryRepository;
import com.example.web_projekat.repositories.dto.category.CategoryDto;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        return categoryRepository.createCategory(categoryDto);
    }

    public List<Category> getAllCategory(int page){
        return categoryRepository.getAllCategory(page);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.getAllCategory();
    }

    public Category findCategoryById(int id){
        return categoryRepository.findCategoryById(id);
    }

    public List<News> getNewsByCategoryId(int id, int page) {
       return categoryRepository.getNewsByCategoryId(id, page);
    }

    public Category updateCategory(int id, CategoryDto categoryDto){
        return categoryRepository.updateCategory(id, categoryDto);
    }

    public void deleteCategoryById(int id){
        categoryRepository.deleteCategoryById(id);
    }
}
