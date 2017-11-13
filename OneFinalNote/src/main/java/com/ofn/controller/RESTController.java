package com.ofn.controller;

import com.ofn.model.BlogPost;
import com.ofn.model.Comment;
import com.ofn.model.Page;
import com.ofn.model.User;
import com.ofn.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class RESTController {
    private BlogService service;

    public RESTController(BlogService service){
        this.service = service;
    }

    @RequestMapping(value = "/displayStaticPage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Page displayStaticPage(@PathVariable("id") int id) {
        return service.getPageById(id);
    }

    @RequestMapping(value = "/getCommentsForBlogPost/{id}/{isOwner}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> getCommentsForBlogPost(@PathVariable("id") int id,
                                                @PathVariable("isOwner") boolean isOwner){

        return service.getCommentsForPost(id, isOwner);
    }

    @RequestMapping(value = "/displayBlogPost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BlogPost displayBlogPost(@PathVariable("id") int id){
        return service.getBlogPost(id);
    }

    @RequestMapping(value = "/search/blogs", method = RequestMethod.GET)
    @ResponseBody
    public List<BlogPost> searchposts(HttpServletRequest req){
        String terms=null, method="general", state="both";
        String blogPostID = null, userID = null, published = null,
                tag = null, title = null, categoryID = null, body = null, date = null;

       try {method = req.getParameter("method");} catch (Exception e){}
       try {terms = req.getParameter("terms");} catch (Exception e){}
       try {state = req.getParameter("state");} catch (Exception e){}

        state = getState(state);

       if (terms == null || terms.trim().isEmpty()){
           return service.searchBlogPost(null, null, state);
       }

       switch (method){
           case "title": title = terms; break;
           case "general": body = terms; break;
           case "category": categoryID = terms; break;
           case "date": date = terms; break;
           case "tag":
               String[] tags = terms.split(",");
               return service.getByTags(tags);
           case "id": blogPostID = terms; break;
           case "userid": userID = terms; break;
       }


        List<BlogPost> p = service.searchBlogPost(blogPostID,userID,state,
                title,categoryID,body,date);

       return p;
    }

    @RequestMapping(value = "/getUserNames", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getUserNames(HttpServletRequest req){
        List<User> userList = service.getAllUsers();
        List<String> userNames = new ArrayList<>();

        userList.forEach((u) -> {
            userNames.add(u.getUserName());
        });

        return userNames;
    }

    @RequestMapping(value = "/search/pages", method = RequestMethod.GET)
    @ResponseBody
    public List<Page> searchpages(HttpServletRequest req){
        return service.getPublishedPages();
    }

    private String getState(String state){
        if (state == null) return "1";
        else if (state.equals("published")) return "1";
        else if (state.equals("all")) return null;
        else return "0";
    }






}

