package com.upscale.registration.controller;

import com.upscale.registration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParserController {
    @Autowired
    private UserRepository db;

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically/user_list", method = RequestMethod.GET)
    public String showUserList(ModelMap model){
        return "user_list";
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically/user_list", method = RequestMethod.POST)
    public String addNewUsersToDb(ModelMap model){
        // TODO Implement adding user to DB
        return "success";
    }
}
