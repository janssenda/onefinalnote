package com.ofn.model;

public class Tag {

    private String tagText;
    public String getTagText() {
        return tagText;
    }
    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public Tag(){

    }

    public Tag(String tagText){
        this.tagText = tagText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagText != null ? tagText.equals(tag.tagText) : tag.tagText == null;
    }

    @Override
    public int hashCode() {
        return tagText != null ? tagText.hashCode() : 0;
    }
}
