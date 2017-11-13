package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CategoryDaoDbImplTest {

    private CategoryDao dao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("CategoryDao", CategoryDao.class);
        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);
        mDao.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllCategories() throws Exception {
        List<Category> cats = dao.getAllCategories();
        assertEquals(1, cats.size());
    }

    @Test
    public void getCategory() throws Exception {
        Category getcat = dao.getCategory(1);
        assertNotNull(getcat);
        assertEquals("hippie", getcat.getCategoryName());
    }

    @Test
    public void addCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("Rush");
        cat.setDescription("all things Rush");
        Category catadded = dao.addCategory(cat);
        assertEquals(catadded.getCategoryName(), cat.getCategoryName());
    }

    @Test
    public void removeCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("jam band");
        cat.setDescription("all things jam band");
        cat = dao.addCategory(cat);
        boolean isDeleted = dao.removeCategory(cat.getCategoryID());
        assertTrue(isDeleted);
    }

    @Test
    public void updateCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("jam band");
        cat.setDescription("all things jam band");
        cat = dao.addCategory(cat);
        cat.setDescription("news on jam band, rave, and prog rock bands, festivals, and deals");
        boolean isCatEdited = dao.updateCategory(cat);
        assertTrue(isCatEdited);
    }

}