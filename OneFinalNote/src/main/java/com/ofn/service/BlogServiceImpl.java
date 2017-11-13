package com.ofn.service;

import com.ofn.dao.impl.DBMaintenanceDao;
import com.ofn.dao.impl.PersistenceException;
import com.ofn.dao.interfaces.*;
import com.ofn.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogServiceImpl implements BlogService{

    private UserDao uDao;
    private CategoryDao cDao;
    private PageDao pDao;
    private BlogPostDao bpDao;
    private CommentDao coDao;
    private DBMaintenanceDao mDao;

    public BlogServiceImpl(UserDao uDao, CategoryDao cDao, PageDao pDao,
                           BlogPostDao bpDao, CommentDao coDao, DBMaintenanceDao mDao){
        this.uDao = uDao;
        this.cDao = cDao;
        this.pDao = pDao;
        this.bpDao = bpDao;
        this.coDao = coDao;
        this.mDao = mDao;
    }

    @Override
    public void refresh(){
        this.mDao.refresh();
    }

    @Override
    public List<Tag> getAllTags() {
        return bpDao.getAllTags();
    }

    @Override
    public boolean addTag(Tag tag) throws PersistenceException {
        boolean isAdded = bpDao.addTag(tag);
        if(!isAdded){
            throw new PersistenceException("Couldn't add tag");
        }
        return isAdded;
    }

    @Override
    public List<Category> getAllCategories() {
        return cDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int catId) {
        return cDao.getCategory(catId);
    }

    @Override
    public Category addCategory(Category category) throws PersistenceException {
        Category cat = cDao.addCategory(category);
        if(cat == null){
            throw new PersistenceException("Couldn't add category");
        }
        return cat;
    }

    @Override
    public Category updateCategory(Category category) throws PersistenceException {
        boolean isUpdated = cDao.updateCategory(category);
        if(!isUpdated) {
            throw new PersistenceException("Couldn't update category");
        }
        else{
            Category cat = cDao.getCategory(category.getCategoryID());
            if(cat == null) {
                throw new PersistenceException("Error retrieving updated category");
            }
            return cat;
        }
    }

    @Override
    public boolean removeCategory(int catId) throws PersistenceException {
        boolean isDeleted = cDao.removeCategory(catId);
        if(!isDeleted){
            throw new PersistenceException("Couldn't delete category");
        }
        return isDeleted;
    }

    @Override
    public List<User> getAllUsers() {
        return uDao.getAllUsers();
    }

    @Override
    public List<User> searchUsers(String... args) {
        return uDao.searchUsers(args);
    }

    @Override
    public User getUserById(int userId) {
        return uDao.getUserById(userId);
    }

    @Override
    public User getUserByName(String userName){
        return uDao.getUserByName(userName);
    }

    @Override
    public User addUser(User user) throws PersistenceException {
        User u = uDao.addUser(user);
        if(u == null){
            throw new PersistenceException("Error adding user");
        }
        return u;
    }

    @Override
    public User updateUser(User user) throws PersistenceException {
        boolean isUpdated = uDao.updateUser(user);
        if(!isUpdated){
            throw new PersistenceException("Error updating user");
        }
        return uDao.getUserById(user.getUserId());
    }

    @Override
    public boolean removeUser(int userId) throws PersistenceException {
        boolean isDeleted = uDao.removeUser(userId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting user");
        }
        return isDeleted;
    }

    @Override
    public List<Page> getPublishedPages() {
        List<Page> allPages = pDao.getAllPages();
        List<Page> retPages = new ArrayList<>();
        for(Page p : allPages){
            if(p.isPublished()){
                p.setUser(uDao.getUserById(p.getUser().getUserId()));
                retPages.add(p);
            }
        }
        return retPages;
    }

    @Override
    public List<Page> getUnPublishedPages() {
        List<Page> allPages = pDao.getAllPages();
        List<Page> retPages = new ArrayList<>();
        for(Page p : allPages){
            if(!p.isPublished()){
                p.setUser(uDao.getUserById(p.getUser().getUserId()));
                retPages.add(p);
            }
        }
        return retPages;
    }

    @Override
    public Page addPage(Page page) throws PersistenceException {
        Page p = pDao.addPage(page);
        if(p == null){
            throw new PersistenceException("Error adding page");
        }
        p.setUser(uDao.getUserById(p.getUser().getUserId()));
        return p;
    }

    @Override
    public Page updatePage(Page page) throws PersistenceException {
        boolean isUpdated = pDao.updatePage(page);
        if(!isUpdated){
            throw new PersistenceException("Error updating page");
        }
        Page updated = pDao.getPage(page.getPageId());
        updated.setUser(uDao.getUserById(page.getUser().getUserId()));
        return updated;
    }

    @Override
    public Page removePage(int pageId) throws PersistenceException {
        Page p = pDao.getPage(pageId);
        boolean isDeleted = pDao.removePage(pageId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting page");
        }
        return p;
    }

    @Override
    public Page getPageById(int pageId) {
        Page getPage = pDao.getPage(pageId);
        getPage.setUser(uDao.getUserById(getPage.getUser().getUserId()));
        return getPage;
    }

    @Override
    public Comment addComment(Comment comment) throws PersistenceException {
        Comment c = coDao.addComment(comment);
        if(c == null){
            throw new PersistenceException("Error adding comment");
        }
        c.setUser(uDao.getUserById(c.getUser().getUserId()));
        return c;
    }

    @Override
    public Comment updateComment(Comment comment) throws PersistenceException {
        boolean isUpdated = coDao.updateComment(comment);
        if(!isUpdated){
            throw new PersistenceException("Error updating comment");
        }
        Comment updated = coDao.getComment(comment.getCommentId());
        updated.setUser(uDao.getUserById(comment.getUser().getUserId()));
        return updated;
    }

    @Override
    public Comment removeComment(int commentId) throws PersistenceException {
        Comment comment = coDao.getComment(commentId);
        boolean isDeleted = coDao.removeComment(commentId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting comment");
        }
        return comment;
    }

    @Override
    public Comment getCommentById(int commentId) {
        Comment commentById = coDao.getComment(commentId);
        commentById.setUser(uDao.getUserById(commentById.getUser().getUserId()));
        return commentById;
    }

    @Override
    public List<Comment> getCommentsForPost(int blogPostId, boolean isOwner){
        List<Comment> commentsForPost = coDao.getCommentsForPost(blogPostId);
        List<Comment> publishedComments = new ArrayList<>();
        for(Comment cfp : commentsForPost){
            if(cfp.isPublished()) {
                cfp.setUser(uDao.getUserById(cfp.getUser().getUserId()));
                publishedComments.add(cfp);
            }
        }
        if(isOwner){
            return commentsForPost;
        }
        return publishedComments;
    }

    @Override
    public List<BlogPost> getPublishedPosts() {
        List<BlogPost> pubPosts = bpDao.getAllPubBlogPosts();
        for(BlogPost pubPost : pubPosts){
            pubPost.setUser(uDao.getUserById(pubPost.getUserId()));
            List<Comment> comms = coDao.getCommentsForPost(pubPost.getBlogPostId());
            comms = addUsersToComments(comms);
            pubPost.setCommentList(comms);
        }
        return pubPosts;
    }

    @Override
    public List<BlogPost> getUnPublishedPosts() {
        List<BlogPost> unpubPosts = bpDao.getAllUnPubBlogPosts();
        for(BlogPost up : unpubPosts){
            up.setUser(uDao.getUserById(up.getUserId()));
            List<Comment> comms = coDao.getCommentsForPost(up.getBlogPostId());
            comms = addUsersToComments(comms);
            up.setCommentList(comms);
        }
        return unpubPosts;
    }

    @Override
    public List<BlogPost> getAllPosts() {
        List<BlogPost> allPub = getPublishedPosts();
        List<BlogPost> allUnpub = getUnPublishedPosts();
        allPub.addAll(allUnpub);
        return allPub;
    }

    public List<Comment> addUsersToComments(List<Comment> in){
        for(Comment i : in){
            i.setUser(uDao.getUserById(i.getUser().getUserId()));
        }
        return in;
    }

    @Override
    public List<BlogPost> searchBlogPost(String... args) {
        List<BlogPost> searched = bpDao.searchBlogPosts(args);
        for(BlogPost s: searched){
            s.setUser(uDao.getUserById(s.getUserId()));
            List<Comment> comms = coDao.getCommentsForPost(s.getBlogPostId());
            comms = addUsersToComments(comms);
            s.setCommentList(comms);
        }
        return searched;
    }

    @Override
    public BlogPost addPost(BlogPost post) throws PersistenceException {
        post.setUser(uDao.getUserById(post.getUserId()));
        
        //unsure if we want to include this functionality
        post = parseTags(post);
        
        BlogPost bp = bpDao.addBlogPost(post);
        if(bp == null){
            throw new PersistenceException("Error adding blog post");
        }
        return bp;
    }

    @Override
    public BlogPost updatePost(BlogPost post) throws PersistenceException {
        post.setUser(uDao.getUserById(post.getUserId()));
        post = parseTags(post);
        boolean isUpdated = bpDao.updateBlogPost(post);
        if(!isUpdated){
            throw new PersistenceException("Error updating blog post");
        }
        return post;
    }

    @Override
    public BlogPost removePost(int blogPostId) throws PersistenceException {
        BlogPost bd = bpDao.getBlogPostById(blogPostId);
        boolean isDeleted = bpDao.removeBlogPost(blogPostId);
        if(!isDeleted){
            throw new PersistenceException("Error deleting blog post");
        }
        return bd;
    }

    @Override
    public BlogPost getBlogPost(int blogPostId) {
        BlogPost getPost = bpDao.getBlogPostById(blogPostId);
        getPost.setUser(uDao.getUserById(getPost.getUserId()));
        List<Comment> commsForPost = coDao.getCommentsForPost(getPost.getBlogPostId());
        commsForPost = addUsersToComments(commsForPost);
        getPost.setCommentList(commsForPost);
        return getPost;
    }

    @Override
    public List<BlogPost> getByTags(String[] tags) {
        List<BlogPost> postsForTags = new ArrayList<>();
        for(String tag : tags){
            Tag t = new Tag();
            t.setTagText(tag);
            List<BlogPost> postsForTag = getPostsByTag(t);
            postsForTags.addAll(postsForTag);
        }
        for(BlogPost pft : postsForTags){
            pft.setUser(uDao.getUserById(pft.getUserId()));
            List<Comment> comms = coDao.getCommentsForPost(pft.getBlogPostId());
            comms = addUsersToComments(comms);
            pft.setCommentList(comms);
        }
        return postsForTags;
    }

    @Override
    public List<BlogPost> getPostsByUserId(int userId) {
       List<BlogPost> getPosts = bpDao.getByUser(userId);
       for(BlogPost gp : getPosts){
           gp.setUser(uDao.getUserById(gp.getUserId()));
           List<Comment> comms = coDao.getCommentsForPost(gp.getBlogPostId());
           comms = addUsersToComments(comms);
           gp.setCommentList(comms);
       }
       return getPosts;
    }

    @Override
    public List<BlogPost> getPostsByTag(Tag tag) {
//        yet to implement;
        List<BlogPost> postsByTag = bpDao.getBlogPostsByTag(tag);
        for(BlogPost pbt : postsByTag){
            pbt.setUser(uDao.getUserById(pbt.getUserId()));
            List<Comment> comms = coDao.getCommentsForPost(pbt.getBlogPostId());
            comms = addUsersToComments(comms);
            pbt.setCommentList(comms);
        }
        return postsByTag;
    }

    @Override
    public Map<Integer, String> getPageLinks(){
        return pDao.getLinks();

    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> commentsForUser = coDao.getCommentsByUserId(userId);
        List<Comment> publishedComments = new ArrayList<>();
        for(Comment cfu : commentsForUser){
            if(cfu.isPublished()) {
                cfu.setUser(uDao.getUserById(cfu.getUser().getUserId()));
                publishedComments.add(cfu);
            }
        }
        return publishedComments;
    }
    
     //further implementation: make the hashtag text bold or a URL?
    public BlogPost parseTags(BlogPost bp){

        ArrayList<Tag> tagList = new ArrayList<>();
        ArrayList<String> hashText = new ArrayList<>();

        String regex = "(?:\\s|\\A|>)[##]+([A-Za-z0-9-_]+)";
        Pattern regPat = Pattern.compile(regex);
        Matcher m = regPat.matcher(bp.getBody());
        while(m.find()){
            String str = m.group().split("#")[1];
            hashText.add(str);
            Tag toAdd = new Tag();
            toAdd.setTagText(str);
            tagList.add(toAdd);
        }
        
        for(String hashtag: hashText){
            bp.setBody(bp.getBody().replaceAll("#"+hashtag,
                    "<a class='bodytag' href='./search?cat=blog&method=tag&state=published&terms=" +
                            hashtag +"'>#"+hashtag+"</a>"));
        }
        
        bp.setTagList(tagList);
        return bp;
      
    }
}
