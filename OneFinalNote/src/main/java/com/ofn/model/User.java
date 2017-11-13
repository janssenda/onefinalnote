/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ofn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String userName;
    private String userPW;
    private String userAvatar;
    private String userProfile;
    private boolean isEnabled;
    private List<String> authorities = new ArrayList<>();
    private List<Comment> userComments;

    public List<String> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
    public List<Comment> getUserComments() {return userComments;}
    public void setUserComments(List<Comment> userComments) { this.userComments = userComments;  }
    public int getUserId(){
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPW() {
        return userPW;
    }
    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }
    public String getUserAvatar() {
        return userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    public String getUserProfile() {
        return userProfile;
    }
    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }
    public void addAuthority(String authority) {authorities.add(authority);}
    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }

    public boolean getIsEnabled(){
        return this.isEnabled;
    }


    public User(){
        this.isEnabled = true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;
        if(!Objects.equals(this.userId, user.userId)){
            return false;
        }
        if(!Objects.equals(this.userName, user.userName)){
            return false;
        }
        if(!Objects.equals(this.userPW, user.userPW)){
            return false;
        }
        if(!Objects.equals(this.userAvatar, user.userAvatar)){
            return false;
        }
        if(!Objects.equals(this.userProfile, user.userProfile)){
            return false;
        }
        if(!Objects.equals(this.isEnabled, user.isEnabled)){
            return false;
        }
        if(!Objects.equals(this.userComments, user.userComments)){
            return false;
        }
        if(!Objects.equals(this.authorities, user.authorities)){
            return false;
        }
        return true;
//        if (userId != user.userId) return false;
//        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
//        if (userPW != null ? !userPW.equals(user.userPW) : user.userPW != null) return false;
//        if (userAvatar != null ? !userAvatar.equals(user.userAvatar) : user.userAvatar != null) return false;
//        if (userProfile != null ? !userProfile.equals(user.userProfile) : user.userProfile != null) return false;
//        if (authorities != null ? !authorities.equals(user.authorities) : user.authorities != null) return false;
//        return userComments != null ? userComments.equals(user.userComments) : user.userComments == null;
    }

    @Override
    public int hashCode() {
//        int result = userId;
//        result = 31 * result + (userName != null ? userName.hashCode() : 0);
//        result = 31 * result + (userPW != null ? userPW.hashCode() : 0);
//        result = 31 * result + (userAvatar != null ? userAvatar.hashCode() : 0);
//        result = 31 * result + (userProfile != null ? userProfile.hashCode() : 0);
//        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
//        result = 31 * result + (userComments != null ? userComments.hashCode() : 0);
//        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        int result = 3;
        result = 29 * result + Objects.hashCode(this.userId);
        result = 29 * result + Objects.hashCode(this.userName);
        result = 29 * result + Objects.hashCode(this.userPW);
        result = 29 * result + Objects.hashCode(this.userAvatar);
        result = 29 * result + Objects.hashCode(this.userProfile);
        result = 29 * result + Objects.hashCode(this.isEnabled);
        result = 29 * result + Objects.hashCode(this.authorities);
        result = 29 * result + Objects.hashCode(this.userComments);
        return result;
    }
}
