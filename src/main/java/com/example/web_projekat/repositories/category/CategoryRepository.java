package com.example.web_projekat.repositories.category;

import com.example.web_projekat.entities.Category;
import com.example.web_projekat.entities.News;
import com.example.web_projekat.repositories.dto.category.CategoryDto;

import java.util.List;

public interface CategoryRepository {

    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategory(int page);

    List<Category> getAllCategory();

    Category findCategoryById(int id);


    List<News> getNewsByCategoryId(int id, int page);

    Category updateCategory(int id, CategoryDto categoryDto);

    void deleteCategoryById(int id);
}
