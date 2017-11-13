package com.ofn.service;

import com.ofn.dao.impl.PersistenceException;
import com.ofn.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlogServiceImplTest {

    private BlogService service;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        service = ctx.getBean("BlogService", BlogService.class);
        service.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllTags() throws Exception {
        List<Tag> allTags = service.getAllTags();
        assertTrue(!allTags.isEmpty());
    }

    @Test
    public void addTag() throws PersistenceException {
        try {
            Tag t = new Tag();
            t.setTagText("Rush");
            service.addTag(t);
        }
        catch(PersistenceException ex){
            fail("Should have added valid tag");
        }
    }

    @Test
    public void getAllCategories() throws Exception {
        List<Category> allCats = service.getAllCategories();
        assertTrue(!allCats.isEmpty());
    }

    @Test
    public void getCategoryById() throws Exception {
        Category cat = service.getCategoryById(1);
        assertEquals("hippie",cat.getCategoryName());
    }

    @Test
    public void addCategory() throws PersistenceException {
        try {
            Category cat = new Category();
            cat.setCategoryName("Bonnaroo");
            cat.setDescription("All news, schedules, and happenings at Bonnaroo festival");
            cat = service.addCategory(cat);
            assertNotNull(cat);
            assertEquals("Bonnaroo", cat.getCategoryName());
        }
        catch(PersistenceException ex){
            fail("Should've added valid category");
        }
    }

    @Test
    public void updateCategory() throws Exception {
        try {
            Category cat = new Category();
            cat.setCategoryName("Bonnaroo festival");
            cat.setDescription("All news, schedules, and happenings at Bonnaroo festival");
            cat = service.addCategory(cat);
            cat.setCategoryName("Bonnaroo music festival");
            cat.setDescription("All news, schedules, and happenings at Bonnaroo festival");
            Category editedCat = service.updateCategory(cat);
            assertEquals(cat.getCategoryID(),editedCat.getCategoryID());
        }
        catch(PersistenceException ex){
            fail("Should've edited category");
        }
    }

    @Test
    public void removeCategory() throws PersistenceException {
        try {
            Category cat = new Category();
            cat.setCategoryName("SXSW");
            cat.setDescription("All news, schedules, and happenings at SXSW music festival");
            cat = service.addCategory(cat);
            boolean isDeleted = service.removeCategory(cat.getCategoryID());
            assertTrue(isDeleted);
        }
        catch(PersistenceException ex){
            fail("Should've edited category");
        }
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> allUsers = service.getAllUsers();
        assertTrue(!allUsers.isEmpty());
    }

    @Test
    public void searchUsers() throws Exception {
        List<User> usersSearched = service.searchUsers("1");
        assertEquals(1,usersSearched.size());
        assertEquals("sethroTull",usersSearched.get(0).getUserName());
    }

    @Test
    public void getUserById() throws Exception {
        User u = service.getUserById(1);
        assertNotNull(u);
        assertEquals("sethroTull",u.getUserName());
    }

    @Test
    public void addUser() throws PersistenceException {
        try {
            User u = new User();
            u.setUserName("sethymus");
            u.setUserPW("cornwolf");
            u.setUserAvatar("rockinrabbi.png");
            u.setUserProfile("ofn.org/users/sethymus");
            ArrayList<String> authoritah = new ArrayList<>();
            authoritah.add("user");
            authoritah.add("admin");
            authoritah.add("owner");
            u.setAuthorities(authoritah);
            u.setUserComments(new ArrayList<>());
            u = service.addUser(u);
            assertNotNull(u);
        } catch (PersistenceException ex){
            fail("Should've added valid user");
        }
    }

    @Test
    public void updateUser() throws PersistenceException {
        try {
            User u = new User();
            u.setUserName("sethymus");
            u.setUserPW("cornwolf");
            u.setUserAvatar("rockinrabbi.png");
            u.setUserProfile("ofn.org/users/sethymus");
            ArrayList<String> authoritah = new ArrayList<>();
            authoritah.add("user");
            authoritah.add("admin");
            u.setAuthorities(authoritah);
            u.setUserComments(new ArrayList<>());
            u = service.addUser(u);
            List<String> auth = u.getAuthorities();
            auth.add("owner");
            u.setAuthorities(auth);
            User updated = service.updateUser(u);
            assertNotNull(updated);
            assertEquals(updated.getUserName(), u.getUserName());
        } catch (PersistenceException ex){
            fail("Should've added valid user");
        }
    }

    @Test
    public void removeUser() throws PersistenceException {
        try {
            User u = new User();
            u.setUserName("sethymus");
            u.setUserPW("cornwolf");
            u.setUserAvatar("rockinrabbi.png");
            u.setUserProfile("ofn.org/users/sethymus");
            ArrayList<String> authoritah = new ArrayList<>();
            authoritah.add("user");
            authoritah.add("admin");
            u.setAuthorities(authoritah);
            u.setUserComments(new ArrayList<>());
            u = service.addUser(u);
            boolean isDeleted = service.removeUser(u.getUserId());
            assertTrue(isDeleted);
        } catch (PersistenceException ex){
            fail("Should've added valid user");
        }
    }

    @Test
    public void getPublishedPages() throws Exception {
        List<BlogPost> pubPages = service.getPublishedPosts();
        assertEquals(1, pubPages.size());
    }

    @Test
    public void getUnPublishedPages() throws Exception {
        List<BlogPost> unpubPages = service.getUnPublishedPosts();
        assertEquals(1, unpubPages.size());
    }

    @Test
    public void addPage() throws PersistenceException {
        try {
            Page p = new Page();
            User u = service.getUserById(1);
            p.setUser(u);
            p.setTitle("Senora Lonza rules!");
            p.setBody("<html>The stars of truTV's Impractical Jokers are now New York's hottest new metal band!</html>");
            p.setUpdatedTime(LocalDateTime.now());
            p.setPublished(true);
            p = service.addPage(p);
            assertNotNull(p);
            assertEquals("Senora Lonza rules!",p.getTitle());
        }
        catch (PersistenceException ex){
            fail("Should've added page");
        }
    }

    @Test
    public void updatePage() throws Exception {
        try {
            Page p = new Page();
            User u = service.getUserById(1);
            p.setUser(u);
            p.setTitle("Senora Lonza rules!");
            p.setBody("<html>The stars of truTV's Impractical Jokers are now New York's hottest new metal band!</html>");
            p.setUpdatedTime(LocalDateTime.now());
            p.setPublished(true);
            p = service.addPage(p);
            p.setTitle("Senora Lonza rules!!");
            p = service.updatePage(p);
            assertNotNull(p);
            assertEquals("Senora Lonza rules!!", p.getTitle());
        }
        catch (PersistenceException ex){
            fail("Should've updated page");
        }
    }

    @Test
    public void removePage() throws Exception {
        try {
            Page p = new Page();
            User u = service.getUserById(1);
            p.setUser(u);
            p.setTitle("Senora Lonza rules!");
            p.setBody("<html>The stars of truTV's Impractical Jokers are now New York's hottest new metal band!</html>");
            p.setUpdatedTime(LocalDateTime.now());
            p.setPublished(true);
            p = service.addPage(p);
            p = service.removePage(p.getPageId());
            assertNotNull(p);
        }
        catch (PersistenceException ex){
            fail("Should've updated page");
        }
    }

    @Test
    public void getPageById() throws Exception {
        Page p = service.getPageById(1);
        assertNotNull(p);
        assertEquals("Welcome to One Final Note", p.getTitle());
    }

    @Test
    public void addComment() throws Exception {
        Comment comm = new Comment();
        comm.setCommentTime(LocalDateTime.now());
        comm.setBody("What a rush, bro!");
        comm.setPublished(true);
        comm.setBlogPostId(1);
        comm.setUser(service.getUserById(1));
        comm = service.addComment(comm);
        assertEquals("What a rush, bro!", comm.getBody());
    }

    @Test
    public void updateComment() throws Exception {
        Comment comm = new Comment();
        comm.setCommentTime(LocalDateTime.now());
        comm.setBody("What a rush, bro!");
        comm.setPublished(true);
        comm.setBlogPostId(1);
        comm.setUser(service.getUserById(1));
        comm = service.addComment(comm);
        comm.setBody("What a rush!");
        comm = service.updateComment(comm);
        assertEquals("What a rush!", comm.getBody());
    }

    @Test
    public void removeComment() throws Exception {
        Comment comm = new Comment();
        comm.setCommentTime(LocalDateTime.now());
        comm.setBody("What a rush, bro!");
        comm.setPublished(true);
        comm.setBlogPostId(1);
        comm.setUser(service.getUserById(1));
        comm = service.addComment(comm);
        comm = service.removeComment(comm.getCommentId());
        assertNotNull(comm);
        assertEquals("What a rush, bro!", comm.getBody());
    }

    @Test
    public void getCommentById() throws PersistenceException {
        Comment c = service.getCommentById(1);
        assertNotNull(c);
        assertEquals("Seriously, though, it is quite the... rush. See what I did there?!", c.getBody());
    }

    @Test
    public void getPublishedPosts() throws Exception {
        List<BlogPost> pubPosts = service.getPublishedPosts();
        assertNotNull(pubPosts);
        assertEquals(1, pubPosts.size());
    }

    @Test
    public void getUnPublishedPosts() throws Exception {
        List<BlogPost> unpubPages = service.getUnPublishedPosts();
        assertNotNull(unpubPages);
        assertEquals(1, unpubPages.size());
    }

    @Test
    public void getAllPosts() throws Exception {
        List<BlogPost> allPosts = service.getAllPosts();
        assertEquals(2, allPosts.size());
    }

    @Test
    public void searchBlogPost() throws Exception {
        List<BlogPost> searchedBlogPost = service.searchBlogPost(null,null,null,"Rush On Shrooms Rules!");
        assertEquals(1, searchedBlogPost.size());
    }

//    @Test
//    public void addPost() throws Exception {
//        try {
//            BlogPost newBP = new BlogPost();
//            newBP.setCommentList(new ArrayList<>());
//            newBP.setPublished(true);
//            newBP.setBody("<html>Load up on weed, shrooms and all your other psychedelics. It's almost time for Bonnaroo. Ticket prices are going sky high. Higher than you'll be at Bonnaroo. Is a cosmic odyssey worth breaking the bank?</html>");
//            newBP.setTitle("Bonnaroo and the cosmic price for your cosmic high");
//            newBP.setCategoryId(1);
//            newBP.setUserId(1);
//            List<Tag> tl = new ArrayList<>();
//            Tag t = new Tag();
//            t.setTagText("hippie");
//            tl.add(t);
//            newBP.setTagList(tl);
//            newBP.setUpdateTime(LocalDateTime.now());
//            newBP.setStartDate(LocalDateTime.now());
//            newBP.setEndDate(LocalDateTime.now().plusMonths(1));
//            newBP.setStatus();
//            newBP = service.addPost(newBP);
//            assertNotNull(newBP);
//        }
//        catch(PersistenceException ex){
//            fail("Should've added valid post");
//        }
//    }

//    @Test
//    public void updatePost() throws Exception {
//        try {
//            BlogPost newBP = new BlogPost();
//            newBP.setCommentList(new ArrayList<>());
//            newBP.setPublished(true);
//            newBP.setBody("<html>Load up on weed, shrooms and all your other psychedelics. It's almost time for Bonnaroo. Ticket prices are going sky high. Higher than you'll be at Bonnaroo. Is a cosmic odyssey worth breaking the bank?</html>");
//            newBP.setTitle("Bonnaroo and the cosmic price for your cosmic high");
//            newBP.setCategoryId(1);
//            newBP.setUserId(1);
//            List<Tag> tl = new ArrayList<>();
//            Tag t = new Tag();
//            t.setTagText("hippie");
//            tl.add(t);
//            newBP.setTagList(tl);
//            newBP.setUpdateTime(LocalDateTime.now());
//            newBP.setStartDate(LocalDateTime.now());
//            newBP.setEndDate(LocalDateTime.now().plusMonths(1));
//            newBP.setStatus();
//            newBP = service.addPost(newBP);
//            newBP.setTitle("Bonnaroo: High times and high prices");
//            newBP.setUpdateTime(LocalDateTime.now());
//            newBP.setStatus();
//            newBP = service.updatePost(newBP);
//            assertNotNull(newBP);
//            assertEquals("Bonnaroo: High times and high prices", newBP.getTitle());
//        }
//        catch(PersistenceException ex){
//            fail("Should've added valid post");
//        }
//    }

//    @Test
//    public void removePost() throws Exception {
//        try {
//            BlogPost newBP = new BlogPost();
//            newBP.setCommentList(new ArrayList<>());
//            newBP.setPublished(true);
//            newBP.setBody("<html>Load up on weed, shrooms and all your other psychedelics. It's almost time for Bonnaroo. Ticket prices are going sky high. Higher than you'll be at Bonnaroo. Is a cosmic odyssey worth breaking the bank?</html>");
//            newBP.setTitle("Bonnaroo and the cosmic price for your cosmic high");
//            newBP.setCategoryId(1);
//            newBP.setUserId(1);
//            List<Tag> tl = new ArrayList<>();
//            Tag t = new Tag();
//            t.setTagText("hippie");
//            tl.add(t);
//            newBP.setTagList(tl);
//            newBP.setUpdateTime(LocalDateTime.now());
//            newBP.setStartDate(LocalDateTime.now());
//            newBP.setEndDate(LocalDateTime.now().plusMonths(1));
//            newBP.setStatus();
//            newBP = service.addPost(newBP);
//            newBP = service.removePost(newBP.getBlogPostId());
//            assertNotNull(newBP);
//        }
//        catch(PersistenceException ex){
//            fail("Should've added valid post");
//        }
//    }

    @Test
    public void getBlogPost() throws Exception {
        BlogPost post = service.getBlogPost(1);
        assertNotNull(post);
        assertEquals("Rush On Shrooms Rules!", post.getTitle());
    }

    @Test
    public void getByTags() throws Exception {
        List<BlogPost> postsForTags = service.getByTags(new String[]{"hippie"});
        assertTrue(!postsForTags.isEmpty());
        assertEquals(1, postsForTags.size());
    }

    @Test
    public void getPostsByUserId() throws Exception {
        List<BlogPost> postsForUser = service.getPostsByUserId(1);
        assertTrue(!postsForUser.isEmpty());
        assertEquals(2, postsForUser.size());
    }

    @Test
    public void getPostsByTag() throws PersistenceException {
        Tag t = new Tag();
        t.setTagText("hippie");
        List<BlogPost> postsForTag = service.getPostsByTag(t);
        assertEquals(1, postsForTag.size());
    }

//    @Test
//    public void getCommentsByUserId() throws Exception {
//        List<Comment> commentsForUser = service.getCommentsByUserId(1);
//        assertNotNull(commentsForUser);
//        assertEquals(2, commentsForUser.size());
//    }

    
    @Test
    public void testParse(){
        BlogPost orig = new BlogPost();
        orig.setBody("<p> #holy hell this #parser is <br> <strong>#coolAF</strong>");
       orig = service.parseTags(orig);
       
       BlogPost expected = new BlogPost();
       Tag holy = new Tag();
       holy.setTagText("holy");
       Tag parser = new Tag();
       parser.setTagText("parser");
       Tag coolAF = new Tag();
       coolAF.setTagText("ccolAF");
       expected.setBody("<p> <a href='matchingTagURL'>#holy</a> hell this <a href='matchingTagURL'>#parser</a> is <br> <strong><a href='matchingTagURL'>#coolAF</a></strong>");
       List<Tag> expectedList = new ArrayList<>();
       expectedList.add(holy);
       expectedList.add(parser);
       expectedList.add(coolAF);
       
       expected.setTagList(expectedList);
       
       assertEquals(orig,expected);
        
    }
}