package com.ofn.dao.interfaces;

import com.ofn.model.Category;

import java.util.List;

public interface CategoryDao{

    List<Category> getAllCategories();
    Category getCategory(int categoryId);
    Category addCategory(Category category);
    boolean removeCategory(int categoryId);
    boolean updateCategory(Category category);

}
