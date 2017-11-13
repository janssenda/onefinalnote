package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/*
*
*   @author SethroTull
*
* */
public class CategoryDaoDbImpl implements CategoryDao {

    private static final String SQL_GET_ALL_CATEGORIES
            = "select * from categories";

    private static final String SQL_GET_CATEGORY_BY_ID
            = "select * from categories where CategoryID = ?";

    private static final String SQL_INSERT_CATEGORY
            = "insert into categories (CategoryName, Description) values (?, ?)";

    private static final String SQL_REMOVE_CATEGORY
            = "delete from categories where CategoryID = ?";

    private static final String SQL_UPDATE_CATEGORY
            = "update categories set CategoryName = ?, Description = ? where CategoryID = ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SQL_GET_ALL_CATEGORIES, new CategoryMapper());
    }

    @Override
    public Category getCategory(int categoryId) {
        return jdbcTemplate.queryForObject(SQL_GET_CATEGORY_BY_ID, new CategoryMapper(), categoryId);
    }

    @Override
    public Category addCategory(Category category) {
        int success = jdbcTemplate.update(SQL_INSERT_CATEGORY, category.getCategoryName(),
                category.getDescription());
        if(success == 1){
            int key = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
            Category cat = jdbcTemplate.queryForObject(SQL_GET_CATEGORY_BY_ID, new CategoryMapper(), key);
            return cat;
        }
        return null;
    }

    @Override
    public boolean removeCategory(int categoryId) {
        int success = jdbcTemplate.update(SQL_REMOVE_CATEGORY, categoryId);
        return success == 1;
    }

    @Override
    public boolean updateCategory(Category category) {
        int success = jdbcTemplate.update(SQL_UPDATE_CATEGORY, category.getCategoryName(),
                category.getDescription(), category.getCategoryID());
        if(success == 1){
            Category cat = jdbcTemplate.queryForObject(SQL_GET_CATEGORY_BY_ID, new CategoryMapper(),
                    category.getCategoryID());
            if(cat.getCategoryID() == category.getCategoryID()){
                return true;
            }
            return false;
        }
        return false;
    }

    public static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category cat = new Category();
            cat.setCategoryID(resultSet.getInt("CategoryID"));
            cat.setCategoryName(resultSet.getString("CategoryName"));
            cat.setDescription(resultSet.getString("Description"));
            return cat;
        }
    }
}
