package org.example.service;

import org.example.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> getAllCategories();
    Map<String, Object> getCategoriesData(String username);
}
