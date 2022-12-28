package com.upscale.registration.controller;

import com.upscale.registration.model.User;
import com.upscale.registration.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;


@Controller
public class AccountController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value="/account", method = RequestMethod.GET)
    public ModelAndView showAccountPage(ModelMap model){
        User user = getCurrentUser();
        return new ModelAndView("account/account", "user", user);
    }

    @RequestMapping(value="/account/settings", method = RequestMethod.GET)
    public ModelAndView changeAccountData(ModelMap model){
        User user = getCurrentUser();
        return new ModelAndView("account/change_account_info","user", user);
    }

    @RequestMapping(value="/account/settings", method = RequestMethod.POST)
    public RedirectView saveChangedData(@ModelAttribute("user") User user, BindingResult result,
                                        ModelMap model){

        User userDb = getCurrentUser();
        userDb.setName(user.getName());
        userDb.setLinkedInLink(user.getLinkedInLink());
        userDb.setLinkedPassword(userDb.getLinkedPassword());
        usersRepository.save(userDb);

        return new RedirectView("/account");
    }

    private User getCurrentUser(){
        // Since we have only one user in database, we can just got the first one from iterator
        Iterator<User> usersIterator = usersRepository.findAll().iterator();
        return usersIterator.next();
    }

}
