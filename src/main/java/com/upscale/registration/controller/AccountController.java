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

        return new ModelAndView("account/sign_in");
    }

    @RequestMapping(value="/account/sign_in", method = RequestMethod.GET)
    public ModelAndView showSignInPage(ModelMap model){

        List<User> users =  (ArrayList<User>) usersRepository.findAll();
        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("users", users);

        return new ModelAndView("account/sign_in", usersMap);

    }

    @RequestMapping(value="/account/change_data", method = RequestMethod.GET)
    public ModelAndView changeAccountData(ModelMap model){
        return new ModelAndView("account/change_account_data");
    }

}
