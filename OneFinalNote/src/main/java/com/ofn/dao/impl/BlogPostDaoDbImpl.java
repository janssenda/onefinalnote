package com.ofn.dao.impl;

import com.ofn.dao.interfaces.BlogPostDao;
import com.ofn.model.BlogPost;
import com.ofn.model.BlogPostTag;
import com.ofn.model.Tag;
import com.ofn.service.BlogService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ofn.dao.impl.DataManipulator.preparify;
import static com.ofn.dao.impl.DataManipulator.searchify;
import static com.ofn.dao.impl.DataManipulator.varArgs;

public class BlogPostDaoDbImpl implements BlogPostDao {

    private static final String GET_USER_QUERY_MULTI =
            "SELECT * FROM blogposts WHERE 1 = 1 " +
                    "AND (@BlogPostID IS NULL OR BlogPostID = @BlogPostID)" +
                    "AND (@UserID IS NULL OR UserID = @UserID) " +
                    "AND (@Title IS NULL OR Title LIKE @Title)" +
                    "AND (@CategoryID IS NULL OR CategoryID = @CategoryID )" +
                    "AND (@Body IS NULL OR Body LIKE @Body)" +
                    "AND (@PostTime IS NULL OR PostTime LIKE @PostTime)" +
                    "AND (@StartDate IS NULL OR StartDate LIKE @StartDate)" +
                    "AND (@EndDate IS NULL OR EndDate LIKE @EndDate)" +
                    "AND (@Published IS NULL OR Published = @Published) " +
                    "ORDER BY PostTime desc";

    private static final String SQL_GET_ALL_TAGS
            = "select * from tags";

    private static final String SQL_ADD_TAG
            = "insert into tags (TagText) values (?)";

    private static final String SQL_REMOVE_TAG
            = "delete from tags where TagText = ?";

    private static final String SQL_ADD_BLOGPOST
            = "insert into blogposts (UserID, PostTime, Title," +
            " CategoryID, Body, StartDate, EndDate, Published) " +
            "values (?,?,?,?,?,?,?,?)";
    private static final String SQL_EDIT_BLOGPOST
            = "update blogposts set UserID = ?, PostTime = ?, Title = ?, " +
            "CategoryID = ?, Body = ?, StartDate = ?, EndDate = ?, " +
            "Published = ? where BlogPostID = ?";

    private static final String SQL_GET_BLOGPOST
            = "select * from blogposts where BlogPostID = ?";

    private static final String SQL_DELETE_BLOGPOST
            = "delete from blogposts where BlogPostID = ?";



    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BlogPost getRandomBlogPost() {
        List<BlogPost> allPosts = getAllPubBlogPosts();
        Random rand = new Random();
        int randomBlogIndex = rand.nextInt(allPosts.size());
        return allPosts.get(randomBlogIndex);
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SQL_GET_ALL_TAGS, new TagMapper());
    }

    @Override
    public boolean addTag(Tag tag) {
        return jdbcTemplate.update(SQL_ADD_TAG, tag.getTagText()) == 1;
    }

    @Override
    public boolean removeTag(String tagText) {
        return jdbcTemplate.update(SQL_REMOVE_TAG, tagText) == 1;
    }

    @Override
    public BlogPost addBlogPost(BlogPost post) {
        int success = jdbcTemplate.update(SQL_ADD_BLOGPOST,
                post.getUser().getUserId(), Timestamp.valueOf(post.getUpdateTime()),
                post.getTitle(), post.getCategoryId(), post.getBody(),
                post.getStartDate(), post.getEndDate(), post.isPublished());
        if(success == 1){
            int key = jdbcTemplate
                    .queryForObject("select LAST_INSERT_ID()", Integer.class);
            BlogPost bp = jdbcTemplate.queryForObject(SQL_GET_BLOGPOST, new BlogMapper(), key);
            if(!post.isPublished()){
                jdbcTemplate.update("update blogposts set Published = ? where BlogPostID = ?", false, bp.getBlogPostId());
                bp.setPublished(false);
            }
            bp.setUser(post.getUser());
            List<Tag> tagList = post.getTagList();
            if(tagList != null && !tagList.isEmpty()) {
                for (Tag t : tagList) {
                    Tag test = null;
                    try{
                        test = jdbcTemplate.queryForObject("select * from tags where TagText = ?",
                                new TagMapper(), t.getTagText());
                    }
                    catch(Exception ex){
                        int isAdded = jdbcTemplate.update(SQL_ADD_TAG, t.getTagText());
                        if(isAdded > 0){
                            test = jdbcTemplate.queryForObject("select * from tags where TagText = ?",
                                    new TagMapper(), t.getTagText());
                        }
                        else{
                            return null;
                        }
                    }
                    finally{
                        jdbcTemplate.update("insert into blogpoststags (BlogPostID, TagText)" +
                            "values (?,?)", bp.getBlogPostId(), test.getTagText());
                    }
                }
            }
            bp.setCommentList(post.getCommentList());
            bp.setTagList(post.getTagList());
            return bp;
        }
        return null;
    }

    @Override
    public boolean updateBlogPost(BlogPost post) {
        int success = jdbcTemplate.update(SQL_EDIT_BLOGPOST, post.getUser().getUserId(),
                Timestamp.valueOf(post.getUpdateTime()), post.getTitle(),
                post.getCategoryId(), post.getBody(), post.getStartDate(),
                post.getEndDate(), post.isPublished(), post.getBlogPostId());
        if(success == 1){
            BlogPost check = jdbcTemplate.queryForObject(SQL_GET_BLOGPOST,
                    new BlogMapper(), post.getBlogPostId());
            if(check.getBlogPostId() == post.getBlogPostId()){
                List<BlogPostTag> blogPostTagsEntries = getAllBlogPostTagBridgeEntries();
                for(BlogPostTag bpt : blogPostTagsEntries){
                    if(bpt.getBlogPostID() == check.getBlogPostId()){
                        removeBlogPostBridgeEntries(bpt.getBlogPostID(), bpt.getTagText());
                    }
                }
                for(Tag t : post.getTagList()){
                    Tag test = null;
                    try{
                        test = jdbcTemplate.queryForObject("select * from tags where TagText = ?",
                                new TagMapper(), t.getTagText());
                    }
                    catch(Exception ex){
                        int isAdded = jdbcTemplate.update(SQL_ADD_TAG, t.getTagText());
                        if(isAdded > 0){
                            test = jdbcTemplate.queryForObject("select * from tags where TagText = ?",
                                    new TagMapper(), t.getTagText());
                        }
                        else{
                            return false;
                        }
                    }
                    finally{
                        jdbcTemplate.update("insert into blogpoststags (BlogPostID, TagText)" +
                                "values (?,?)", check.getBlogPostId(), test.getTagText());
                    }

                }
                check.setTagList(post.getTagList());
                check.setCommentList(post.getCommentList());
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public BlogPost getBlogPostById(int blogPostId){
        return jdbcTemplate.queryForObject(SQL_GET_BLOGPOST, new BlogMapper(), blogPostId);
    }

    @Override
    public List<BlogPost> getBlogPostsByTag(Tag tag){
        String tagText = tag.getTagText();
        List<BlogPost> postsForTag = new ArrayList<>();
        List<BlogPostTag> allBlogPostTagBridgeEntries = getAllBlogPostTagBridgeEntries();
        for(BlogPostTag abptbe : allBlogPostTagBridgeEntries){
            if(abptbe.getTagText().contains(tagText)){
                postsForTag.add(setTagsForPost(getBlogPostById(abptbe.getBlogPostID())));
            }
        }
        return postsForTag;
    }

    private BlogPost setTagsForPost(BlogPost post){
        List<Tag> tagList = new ArrayList<>();
        List<BlogPostTag> allBlogPostTagBridgeEntries = getAllBlogPostTagBridgeEntries();
        for(BlogPostTag abptbe : allBlogPostTagBridgeEntries){
            if(abptbe.getBlogPostID() == post.getBlogPostId()){
                tagList.add(new Tag(abptbe.getTagText()));
            }
        }
        post.setTagList(tagList);
        return post;
    }


    @Override
    public List<BlogPostTag> getAllBlogPostTagBridgeEntries(){
        return jdbcTemplate.query("select * from blogpoststags", new BlogPostTagMapper());
    }

    @Override
    public boolean removeBlogPostBridgeEntries(int blogPostId, String tagText){
        return jdbcTemplate.update("delete from blogpoststags where BlogPostID = ? and TagText = ?", blogPostId, tagText) == 1;
    }


    @Override
    public boolean removeBlogPost(int blogPostId) {
        List<BlogPostTag> bpts = getAllBlogPostTagBridgeEntries();
        for(BlogPostTag b : bpts){
            if(b.getBlogPostID() == blogPostId){
                removeBlogPostBridgeEntries(b.getBlogPostID(), b.getTagText());
            }
        }
        return jdbcTemplate.update(SQL_DELETE_BLOGPOST, blogPostId) == 1;
    }


    // Search Functions

    @Override
    public List<BlogPost> getAllPubBlogPosts() {
        return searchBlogPosts(null,null,"1");
    }

    @Override
    public List<BlogPost> getAllUnPubBlogPosts() {
        return searchBlogPosts(null,null,"0");
    }

    @Override
    public List<BlogPost> getByUser(int userId) {
        return searchBlogPosts(null,Integer.toString(userId));
    }

    @Override
    public List<BlogPost> getByCategory(int categoryId) {
        return searchBlogPosts(null, null, null, null, Integer.toString(categoryId));
    }

    @Override
    @Transactional
    public List<BlogPost> searchBlogPosts(String... args) {

        //BlogPostID, UserID, Published, Title, CategoryID, Body, PostTime, StartDate, EndDate

        String[] allArgs = varArgs(args, 9);
        String setup = "SET @BlogPostID = ?, @UserID = ?, @Published = ?, @Title = ?," +
                "@CategoryID = ?, @Body = ?, @PostTime = ?, " +
                "@StartDate = ?, @EndDate = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, preparify(allArgs[0]));
            ps.setString(2, preparify(allArgs[1]));
            ps.setString(3, preparify(allArgs[2]));
            ps.setString(4, searchify(preparify(allArgs[3])));
            ps.setString(5, preparify(allArgs[4]));
            ps.setString(6, searchify(preparify(allArgs[5])));
            ps.setString(7, searchify(preparify(allArgs[6])));
            ps.setString(8, searchify(preparify(allArgs[7])));
            ps.setString(9, searchify(preparify(allArgs[8])));

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();

            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);


            // Search for posts based on the given criteria,
            List<BlogPost> postList = jdbcTemplate.query(GET_USER_QUERY_MULTI, new BlogMapper());
            postList.forEach((p) -> {
                setTagsForPost(p);
            });
            return postList;

        } catch (SQLException e) {
            return null;
        }
    }

    public static final class BlogMapper implements RowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet resultSet, int i) throws SQLException {

            BlogPost post = new BlogPost();
            post.setBlogPostId(resultSet.getInt("BlogPostID"));
            post.setUserId(resultSet.getInt("UserID"));
            post.setTitle(resultSet.getString("Title"));
            post.setCategoryId(resultSet.getInt("CategoryId"));
            post.setBody(resultSet.getString("Body"));
            post.setUpdateTime(resultSet.getTimestamp("PostTime").toLocalDateTime());
            post.setStartDate(resultSet.getTimestamp("StartDate").toLocalDateTime());
            post.setEndDate(resultSet.getTimestamp("EndDate").toLocalDateTime());
            post.setPublished(resultSet.getBoolean("Published"));
            if(post.isPublished()){
                post.setStatus();
            }

            return post;
        }
    }

    public static final class TagMapper implements RowMapper<Tag>{

        @Override
        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag t = new Tag();
            t.setTagText(resultSet.getString("TagText"));
            return t;
        }
    }

    public static final class BlogPostTagMapper implements RowMapper<BlogPostTag>{

        @Override
        public BlogPostTag mapRow(ResultSet resultSet, int i) throws SQLException {
            BlogPostTag bpt = new BlogPostTag();
            bpt.setBlogPostID(resultSet.getInt("BlogPostID"));
            bpt.setTagText(resultSet.getString("TagText"));
            return bpt;
        }
    }
}
