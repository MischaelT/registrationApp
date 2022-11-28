package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    private UserRepository db;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView showUsers(ModelMap model){

        Map<String, Object> users_map = new HashMap<String, Object>();
        List<User> users = (List<User>) db.findAll();
        users_map.put("users", users);

        return new ModelAndView("users", users_map);
    }

    @RequestMapping(value="/users/new_user", method = RequestMethod.GET)
    public ModelAndView showAddUserPage(){
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping(value="/users/new_user", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model){
        // Make a list of current events to choose in view
        db.save(user);
        return "success";
    }

}
