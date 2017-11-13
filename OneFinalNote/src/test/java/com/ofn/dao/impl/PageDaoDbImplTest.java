package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.dao.interfaces.PageDao;
import com.ofn.model.Category;
import com.ofn.model.Page;
import com.ofn.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PageDaoDbImplTest {

    private PageDao dao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("PageDao", PageDao.class);
        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);
        mDao.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addPage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for a new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("a static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        p = dao.addPage(p);
        assertEquals(1, p.getUser().getUserId());
        assertEquals("a static page", p.getTitle());
    }

    @Test
    public void updatePage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        p = dao.addPage(p);
        p.setTitle("my first edited static page");
        p.setBody("dynamic html code for the new static code that is expanded");
        p.setUpdatedTime(LocalDateTime.now());
        boolean isEdited = dao.updatePage(p);
        assertTrue(isEdited);
    }

    @Test
    public void removePage() throws Exception {
        Page p = new Page();
        p.setPublished(true);
        p.setBody("dynamic html code for the new static page");
        p.setUpdatedTime(LocalDateTime.now());
        p.setTitle("my first static page");
        User u = new User();
        u.setUserId(1);
        u.setUserName("sethroTull");
        u.setUserPW("cornwolf");
        u.setUserAvatar("sethroTullTheWerewolf.jpg");
        u.setUserProfile("ofn.org/users/sethroTull");
        p.setUser(u);
        p = dao.addPage(p);
        boolean isDeleted = dao.removePage(p.getPageId());
        assertTrue(isDeleted);
    }

    @Test
    public void getPage() throws Exception {
        Page newPage = dao.getPage(1);
        assertEquals("Welcome to One Final Note",  newPage.getTitle());
    }

    @Test
    public void getLinks() throws Exception {
        Map<Integer,String> pageLinks = dao.getLinks();
        assertEquals(1, pageLinks.size());
    }

    @Test
    public void getAllPages() throws Exception {
        List<Page> allPages = dao.getAllPages();
        assertEquals(2, allPages.size());
    }

}