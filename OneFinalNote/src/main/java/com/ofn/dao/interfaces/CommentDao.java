package com.ofn.dao.interfaces;

import com.ofn.model.Comment;
import java.util.List;

public interface CommentDao {

    List<Comment> getCommentsForPost (int blogPostID);
    List<Comment> getAllComments();
    Comment addComment(Comment comment);
    boolean removeComment(int commentId);
    boolean updateComment(Comment comment);
    Comment getRandomComment();
    Comment getComment(int commentId);
    List<Comment> getCommentsByUserId(int userId);
}
