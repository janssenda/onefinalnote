package com.ofn.dao.interfaces;

import com.ofn.model.BlogPost;
import com.ofn.model.BlogPostTag;
import com.ofn.model.Tag;
import java.util.List;

public interface BlogPostDao {

    List<BlogPost> getAllPubBlogPosts();
    List<BlogPost> getAllUnPubBlogPosts();

    BlogPost addBlogPost(BlogPost post);
    boolean updateBlogPost(BlogPost post);
    boolean removeBlogPost(int blogPostId);
    BlogPost getRandomBlogPost();
    BlogPost getBlogPostById(int blogPostId);
    List<BlogPost> getBlogPostsByTag(Tag tag);

    List<BlogPostTag> getAllBlogPostTagBridgeEntries();
    boolean removeBlogPostBridgeEntries(int blogPostId, String tagText);


    List<Tag> getAllTags();
    boolean addTag(Tag tag);
    boolean removeTag(String tagText);

    List<BlogPost> getByUser(int userID);
    List<BlogPost> getByCategory(int categoryId);
    List<BlogPost> searchBlogPosts(String... args);

}
