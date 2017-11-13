package com.ofn.service;

import com.ofn.dao.impl.PersistenceException;
import com.ofn.model.*;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

public interface BlogService {

    // Basic model CRUD functionality
    // Tags
    List<Tag> getAllTags();
    boolean addTag(Tag tag) throws PersistenceException;

    void refresh();

    // Categories
    List<Category> getAllCategories();
    Category getCategoryById(int catId);
    Category addCategory(Category category) throws PersistenceException;
    Category updateCategory(Category category) throws PersistenceException;
    boolean removeCategory(int catId) throws PersistenceException;

    // Users
    List<User> getAllUsers();
    List<User> searchUsers(String...args);
    User getUserById(int userId);
    User getUserByName(String userName);
    User addUser(User user) throws PersistenceException;
    User updateUser(User user) throws PersistenceException;
    boolean removeUser(int userId) throws PersistenceException;

    // Pages
    List<Page> getPublishedPages();
    List<Page> getUnPublishedPages();
    Page addPage(Page page) throws PersistenceException;
    Page updatePage (Page page) throws PersistenceException;
    Page removePage (int pageId) throws PersistenceException;
    Page getPageById(int pageId);
    Map<Integer, String> getPageLinks();

    // Comments
    Comment addComment(Comment comment) throws PersistenceException;
    Comment updateComment(Comment comment) throws PersistenceException;
    Comment removeComment(int commentId) throws PersistenceException;
    Comment getCommentById(int commentId);
    List<Comment> getCommentsForPost(int blogPostId, boolean isOwner);

    // Blog Posts
    List<BlogPost> getPublishedPosts();
    List<BlogPost> getUnPublishedPosts();
    List<BlogPost> getAllPosts();
    List<BlogPost> searchBlogPost(String...args);

    BlogPost addPost(BlogPost post) throws PersistenceException;
    BlogPost updatePost(BlogPost post) throws PersistenceException;
    BlogPost removePost(int blogPostId) throws PersistenceException;
    BlogPost getBlogPost(int blogPostId);

    // Extended functionality for special queries
    List<BlogPost> getByTags(String[] tags);
    List<BlogPost> getPostsByUserId(int userId);
    List<BlogPost> getPostsByTag(Tag tag);
    List<Comment> getCommentsByUserId(int userId);
    
    //tagParser
    BlogPost parseTags(BlogPost bp);

}
