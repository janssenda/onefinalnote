package com.ofn.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDateTime;

public class Comment {

    private int commentId;
    private int blogPostId;
    private User user;
    private String body;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime commentTime;
    private boolean published;

    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public int getBlogPostId() {
        return blogPostId;
    }
    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public LocalDateTime getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (commentId != comment.commentId) return false;
        if (blogPostId != comment.blogPostId) return false;
        if (published != comment.published) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        if (body != null ? !body.equals(comment.body) : comment.body != null) return false;
        return commentTime != null ? commentTime.equals(comment.commentTime) : comment.commentTime == null;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + blogPostId;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (commentTime != null ? commentTime.hashCode() : 0);
        result = 31 * result + (published ? 1 : 0);
        return result;
    }
}
