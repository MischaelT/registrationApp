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
    private UserRepository user_repository;
    @Autowired
    private EventsRepository event_repository;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView showUsers(ModelMap model){

        Map<String, Object> users_map = new HashMap<String, Object>();
        List<User> users = (List<User>) user_repository.findAll();
        users_map.put("users", users);

        return new ModelAndView("users", users_map);
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserPage(){
        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = event_repository.findByIsPassed(false);
        events_map.put("upcoming_events", events);

        //TODO implement transferring list of events to the view

        ModelAndView modelAndView = new ModelAndView("new_user", "user", new User());

        modelAndView.addObject("upcoming_events", events_map);
        return modelAndView;
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.POST)
    public String addUserManually(@ModelAttribute("user") User user, BindingResult result, ModelMap model){
        //TODO implement transferring list of events to the view
        user_repository.save(user);
        return "success";
    }

    @RequestMapping(value="/users/new_user/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyPage(){
        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = event_repository.findByIsPassed(false);
        events_map.put("upcoming_events", events);
        ModelAndView modelAndView = new ModelAndView("new_user_automatically", "user", new User());

        // TODO implement form to paste the linkedIn link to parse

        return modelAndView;
    }

    @RequestMapping(value="/users/new_user/automatically", method = RequestMethod.POST)
    public String addUserAutomatically(@ModelAttribute("user") User user, BindingResult result, ModelMap model){
        //TODO implement parse linkedIn with Selenium
        user_repository.save(user);
        return "success";
    }
}
