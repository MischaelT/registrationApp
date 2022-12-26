package com.upscale.registration.controller;


import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.AttendeeRepository;
import com.upscale.registration.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class AccountController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value="/account", method = RequestMethod.GET)
    public ModelAndView showAccountPage(ModelMap model){

        Iterator<User> usersIterator = usersRepository.findAll().iterator();
        User user = usersIterator.next();

        return new ModelAndView("account/account", "user", user);
    }

    @RequestMapping(value="/account/settings", method = RequestMethod.GET)
    public ModelAndView changeAccountData(ModelMap model){
        return new ModelAndView("account/change_account_info");
    }

    @RequestMapping(value="/account/settings", method = RequestMethod.POST)
    public ModelAndView saveChangedData(ModelMap model){
        // TODO implement the changing of account info
        return new ModelAndView("account/change_account_info");
    }

}
