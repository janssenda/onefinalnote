package com.ofn.controller;

import com.ofn.dao.impl.PersistenceException;
import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import com.ofn.service.BlogService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class AccountHandlerController {

    private BlogService dao;
    private PasswordEncoder encoder;

    @Inject
    public AccountHandlerController(BlogService dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    // This endpoint retrieves all users from the database and puts the
    // List of users on the model
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String displayUserList(Map<String, Object> model, Model m) {
        List users = dao.getAllUsers();
        model.put("users", users);
        displayPageLinks(m);
        return "accounts";
    }

    public void displayPageLinks(Model m){
        Map<Integer, String> pageLinks = dao.getPageLinks();
        m.addAttribute("pageLinks", pageLinks);
    }


    // This endpoint just displays the Add User form
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String displayUserForm(Map<String, Object> model, Model m) {
        displayPageLinks(m);
        return "signup";
    }

    // This endpoint processes the form data and creates a new User
    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req, Model model) {
        User newUser = new User();
        //model.addAttribute("create-user-error","");

        String userName = req.getParameter("username");
        String clearPw = req.getParameter("password");
        String clearPwCheck = req.getParameter("password-check");

        if (dao.searchUsers(null,userName).size() > 0){
            model.addAttribute("userexistserror","The username has been taken.");
            model.addAttribute("username", userName);
            model.addAttribute("userr",1);
            return "signup";
        }


        if (!clearPw.equals(clearPwCheck)){
            model.addAttribute("pwmismatcherror","Passwords do not match!");
            model.addAttribute("username", userName);
            model.addAttribute("pwerr",1);
            return "signup";
        }

        newUser.setUserName(userName);
        newUser.setUserPW(encoder.encode(clearPw));
        newUser.setUserProfile("ofn.org/users/" + userName);

        newUser.addAuthority("ROLE_USER");
        try {
            dao.addUser(newUser);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
    // This endpoint deletes the specified User
    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("userid") int userId) {
        try {
            dao.removeUser(userId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return "redirect:accounts";
    }


    @RequestMapping(value = "/manageuser", method = RequestMethod.POST)
    public String changeUser(HttpServletRequest req, Model model,@RequestParam("userid") int userId) {

        // Check if the request was for delete
        if (req.getParameter("editbutton").equals("delete")){
            try {
                dao.removeUser(userId);
            } catch (PersistenceException e) {
                // need to add validation
            }
            return "redirect:accounts";
        }
        User user = dao.getUserById(userId);
        // Set enabled status

        if (Boolean.parseBoolean(req.getParameter("enabledbox"))) {
            user.setEnabled(true);
        } else {
            user.setEnabled(false);
        }

        // Update roles if changed
        addUserAuths(user, req.getParameter("roleselect"));

        try {
            dao.updateUser(user);
        }catch (PersistenceException e){
            // add stuff
        }

        return "redirect:accounts";
    }



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model m) {
        displayPageLinks(m);
        return "index";
    }


    private void addUserAuths(User user, String role){
        if (role == null || role.trim().isEmpty()){
            return;
        }

        List<String> userAuths = new ArrayList<>();

        // If the forwarded value matches a valid role, the authorities are added
        // Otherwise, the current setting is intentionally left intact
        switch (role.trim()) {
            case "owner":
                userAuths.add("ROLE_OWNER");
                userAuths.add("ROLE_ADMIN");
                userAuths.add("ROLE_USER");
                user.setAuthorities(userAuths);
                break;
            case "admin":
                userAuths.add("ROLE_ADMIN");
                userAuths.add("ROLE_USER");
                user.setAuthorities(userAuths);
                break;
            case "user":
                userAuths.add("ROLE_USER");
                user.setAuthorities(userAuths);
                break;
        }

    }


}
