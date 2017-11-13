package com.ofn.model;

import java.util.Objects;

public class BlogPostTag {

    private int blogPostID;
    private String tagText;


    public int getBlogPostID() {
        return blogPostID;
    }

    public void setBlogPostID(int blogPostID) {
        this.blogPostID = blogPostID;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.blogPostID);
        hash = 29 * hash + Objects.hashCode(this.tagText);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlogPostTag other = (BlogPostTag) obj;
        if (!Objects.equals(this.blogPostID, other.blogPostID)) {
            return false;
        }
        if (!Objects.equals(this.tagText, other.tagText)) {
            return false;
        }
        return true;
    }

}
