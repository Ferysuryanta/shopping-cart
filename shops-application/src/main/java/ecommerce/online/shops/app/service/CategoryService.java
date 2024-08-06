package ecommerce.online.shops.app.service;

import ecommerce.online.shops.app.model.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
