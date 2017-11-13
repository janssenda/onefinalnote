package com.ofn.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class BlogPost {

    private int blogPostId;
    private int userId;
    private User user;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime updateTime;
    private String title;
    private int categoryId;
    private String body;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime startDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime endDate;
    private boolean published;

    private List<Comment> commentList;
    private List<Tag> tagList;

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<Tag> getTagList() {
        return tagList;
    }
    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
    public int getBlogPostId() {
        return blogPostId;
    }
    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }
    public List<Comment> getCommentList() {
        return commentList;
    }
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }


    public boolean setStatus(){

        LocalDateTime d = LocalDateTime.now();
        published = (d.compareTo(startDate) >= 0 & d.compareTo(endDate)<=0 );
        return published;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BlogPost blogPost = (BlogPost) o;

        if(!Objects.equals(this.blogPostId, blogPost.blogPostId)){
            return false;
        }
        if(!Objects.equals(this.userId, blogPost.userId)){
            return false;
        }
        if(!Objects.equals(this.user, blogPost.user)){
            return false;
        }
        if(!Objects.equals(this.categoryId, blogPost.categoryId)){
            return false;
        }
        if(!Objects.equals(this.published, blogPost.published)){
            return false;
        }
        if(!Objects.equals(this.updateTime, blogPost.updateTime)){
            return false;
        }
        if(!Objects.equals(this.title, blogPost.title)){
            return false;
        }
        if(!Objects.equals(this.body, blogPost.body)){
            return false;
        }
        if(!Objects.equals(this.startDate, blogPost.startDate)){
            return false;
        }
        if(!Objects.equals(this.endDate, blogPost.endDate)){
            return false;
        }
        if(!Objects.equals(this.commentList, blogPost.commentList)){
            return false;
        }
        if(!Objects.equals(this.tagList, blogPost.tagList)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = blogPostId;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (published ? 1 : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        return result;
    }
}
