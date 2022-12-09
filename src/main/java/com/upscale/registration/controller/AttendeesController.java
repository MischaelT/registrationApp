package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EventsRepository eventsRepository;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView showUsers(ModelMap model){

        Map<String, Object> users_map = new HashMap<>();
        List<User> users = (List<User>) usersRepository.findAll();
        users_map.put("users", users);

        return new ModelAndView("users", users_map);
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserPage(){

        Map<String, Object> events_map = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(false);
        events_map.put("upcoming_events", events);

        //TODO implement transferring list of events to the view

        ModelAndView modelAndView = new ModelAndView("new_user", "user", new User());
        modelAndView.addObject("upcoming_events", events_map);

        return modelAndView;
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.POST)
    public String addUserManually(@ModelAttribute("user") User user, BindingResult result, ModelMap model){

        usersRepository.save(user);

        return "success";
    }
}
