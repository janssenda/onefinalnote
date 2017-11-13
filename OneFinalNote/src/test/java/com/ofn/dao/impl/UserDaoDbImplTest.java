package com.ofn.dao.impl;

import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoDbImplTest {

    private UserDao dao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("UserDao", UserDao.class);

        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);
        mDao.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserById() throws Exception {
//        User u = new User();
//        u.setUserName("hlemke91");
//        u.setUserPW("corndog");
//        u.setUserAvatar("hlemkedeathmetalpic.jpg");
//        u.setUserProfile("ofn.org/users/hlemke91");
//        u = dao.addUser(u);
        User getUser = dao.getUserById(6);



        assertNotNull(getUser);
        assertEquals("hlemke91",getUser.getUserName());
//        assertEquals(u.getUserPW(),u.getUserPW());
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> allUsers = dao.getAllUsers();
        assertTrue(allUsers.size() == 6);
    }

    @Test
    public void addUser() throws Exception {
        User addUser = new User();
        addUser.setUserName("phaug");
        addUser.setUserPW("umphreesmcgeerulez");
        addUser.setUserAvatar("phaugjammetal.jpg");
        addUser.setUserProfile("ofn.org/users/phaug");
        User addedUser = dao.addUser(addUser);
        assertEquals(addedUser.getUserName(),addUser.getUserName());
    }

    @Test
    public void updateUser() throws Exception {
        User u = new User();
        u.setUserName("hlemke92");
        u.setUserPW("corndog");
        u.setUserAvatar("hlemkedeathmetalpic.jpg");
        u.setUserProfile("ofn.org/users/hlemke92");
        u = dao.addUser(u);
        int key = u.getUserId();
        User newUser = new User();
        newUser.setUserId(key);
        newUser.setUserName("jstuart");
        newUser.setUserPW("yogibear");
        newUser.setUserAvatar("jstuartjazzmetal.jpg");
        newUser.setUserProfile("ofn.org/users/jstuart");
        newUser.setEnabled(true);
        boolean isUserUpdated = dao.updateUser(newUser);
        assertTrue(isUserUpdated);
    }

    @Test
    public void removeUser() throws Exception {
        User u = new User();
        u.setUserName("hlemke92");
        u.setUserPW("corndog");
        u.setUserAvatar("hlemkedeathmetalpic.jpg");
        u.setUserProfile("ofn.org/users/hlemke91");
        u = dao.addUser(u);
        boolean isRemoved = dao.removeUser(u.getUserId());
        assertTrue(isRemoved);
    }

}