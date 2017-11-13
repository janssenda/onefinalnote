package com.ofn.dao.impl;

import com.ofn.dao.interfaces.PageDao;
import com.ofn.model.Page;
import com.ofn.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDaoDbImpl implements PageDao {

    private static final String SQL_INSERT_PAGE
            = "insert into staticpages (UserID, UpdatedTime," +
            " PageTitle, Body, Published) values (?,?,?,?,?)";
    private static final String SQL_UPDATE_PAGE
            = "update staticpages set UserID = ?, UpdatedTime = ?," +
            " PageTitle = ?, Body = ?, Published = ? where PageID = ?";

    private static final String SQL_GET_PAGE_BY_ID
            = "select * from staticpages where PageID = ?";

    private static final String SQL_REMOVE_PAGE
            = "delete from staticpages where PageID = ?";

    private static final String SQL_GET_ALL_PAGES
            = "select * from staticpages";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page addPage(Page page) {
        int success = jdbcTemplate.update(SQL_INSERT_PAGE, page.getUser().getUserId(),
                Timestamp.valueOf(page.getUpdatedTime()), page.getTitle(), page.getBody(),
                page.isPublished());
        if(success == 1){
            int key = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
            Page p = jdbcTemplate.queryForObject(SQL_GET_PAGE_BY_ID, new PageMapper(), key);
            return p;
        }
        return null;
    }

    @Override
    public boolean updatePage(Page page) {
        int success = jdbcTemplate.update(SQL_UPDATE_PAGE, page.getUser().getUserId(),
                Timestamp.valueOf(page.getUpdatedTime()), page.getTitle(), page.getBody(), page.isPublished(),
                page.getPageId());
        if(success == 1){
            Page p = jdbcTemplate.queryForObject(SQL_GET_PAGE_BY_ID,
                    new PageMapper(), page.getPageId());
            if(page.getPageId() == p.getPageId()){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean removePage(int pageId) {
        return jdbcTemplate.update(SQL_REMOVE_PAGE, pageId) == 1;
    }

    @Override
    public Page getPage(int pageId) {
        return jdbcTemplate.queryForObject(SQL_GET_PAGE_BY_ID, new PageMapper(), pageId);
    }

    @Override
    public List<Page> getAllPages(){
        return jdbcTemplate.query(SQL_GET_ALL_PAGES, new PageMapper());
    }

    @Override
    public Map<Integer, String> getLinks() {
        //method to get list of static pages (for displaying on main page)
        Map<Integer, String> pageLinks = new HashMap<>();
        List<Page> allPages = getAllPages();
        for(Page p : allPages){
            if(p.isPublished()) {
                pageLinks.put(p.getPageId(), p.getTitle());
            }
        }
        return pageLinks;
    }

    public static final class PageMapper implements RowMapper<Page> {

        @Override
        public Page mapRow(ResultSet resultSet, int i) throws SQLException {
            Page p = new Page();
            p.setPageId(resultSet.getInt("PageID"));
            User u = new User();
            u.setUserId(resultSet.getInt("UserID"));
            p.setUser(u);
            p.setUpdatedTime(resultSet
                    .getTimestamp("UpdatedTime").toLocalDateTime());
            p.setTitle(resultSet.getString("PageTitle"));
            p.setBody(resultSet.getString("Body"));
            p.setPublished(resultSet.getBoolean("Published"));
            return p;
        }
    }
}
