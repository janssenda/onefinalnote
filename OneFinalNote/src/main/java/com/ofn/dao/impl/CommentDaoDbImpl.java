/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CommentDao;
import com.ofn.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.ofn.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hayden
 */
public class CommentDaoDbImpl implements CommentDao {
    private final String GET_BY_POST = "select * from comments where BlogPostID=?";
    private final String ADD = "insert into comments (BlogPostID,UserID,Body,CommentTime,Published) values(?,?,?,?,?)";
    private final String DELETE = "delete from comments where CommentID=?";
    private final String UPDATE = "update comments set BlogPostID=?,UserID=?,Body=?,CommentTime=?,Published=? where CommentID=?";
    private final String GET_BY_ID = "select * from comments where CommentID=?";
    private final String GET_BY_USER_ID = "select * from comments where UserID = ?";


    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> getCommentsForPost(int blogPostID) {
        List<Comment> commsForPost = jdbcTemplate.query(GET_BY_POST, new CommentMapper(), blogPostID);
        return commsForPost;
    }

    @Override
    public List<Comment> getAllComments(){
        return jdbcTemplate.query("select * from comments", new CommentMapper());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Comment addComment(Comment comment) {
        jdbcTemplate.update(ADD,
                comment.getBlogPostId(),
                comment.getUser().getUserId(),
                comment.getBody(),
                comment.getCommentTime(),
                comment.isPublished());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        comment.setCommentId(id);
        return comment;
    }

    @Override
    public boolean removeComment(int commentId) {
        return (jdbcTemplate.update(DELETE, commentId) > 0);
    }

    @Override
    public boolean updateComment(Comment comment) {
        return (jdbcTemplate.update(UPDATE,
                comment.getBlogPostId(),
                comment.getUser().getUserId(),
                comment.getBody(),
                comment.getCommentTime(),
                comment.isPublished(),
                comment.getCommentId())
                == 1);
    }

    @Override
    public Comment getComment(int commentId) {
        return jdbcTemplate.queryForObject(GET_BY_ID, new CommentMapper(), commentId);
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return jdbcTemplate.query(GET_BY_USER_ID, new CommentMapper(), userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Comment getRandomComment() {
        Random rng = new Random();
        Comment c;
        do {
            List<Integer> idCollection = jdbcTemplate.queryForList("select CommentID from comments", Integer.class);
            int randID = rng.nextInt(idCollection.size());
            randID = idCollection.get(randID);
            c = jdbcTemplate.queryForObject(GET_BY_ID, new CommentMapper(), randID);
        } while (c.getBody() == null);

        return c;
    }

    private class CommentMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int i) throws SQLException {
            Comment c = new Comment();
            c.setCommentId(rs.getInt("CommentID"));
            c.setBlogPostId(rs.getInt("BlogPostID"));
            c.setBody(rs.getString("Body"));
            User u = new User();
            u.setUserId(rs.getInt("UserID"));
            c.setUser(u);
            c.setCommentTime(rs.getTimestamp("CommentTime").toLocalDateTime());
            c.setPublished(rs.getBoolean("Published"));
            return c;
        }

    }
}
