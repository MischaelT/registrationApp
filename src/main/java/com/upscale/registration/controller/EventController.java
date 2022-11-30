package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventsRepository db;

    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.GET)
    public ModelAndView showUpcomingEvent(@PathVariable int id, ModelMap model){

        Map<String, Object> events_map = new HashMap<String, Object>();

        //TODO check if a event is in database
        List<Event> events = (List<Event>) db.findById(id);
        events_map.put("events", events);

        return new ModelAndView("event", events_map);
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserManuallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.POST)
    public RedirectView addUserManually(@PathVariable int id, @ModelAttribute("user")User user, BindingResult result, ModelMap model){


    //TODO check if event is in database
        List<Event> events = db.findById(id);
        Event event = events.get(0);
        Set<User> user_list = event.getUsers();
        user_list.add(user);
        event.setUsers(user_list);
        db.save(event);

        return new RedirectView("/events/upcoming/{id}");
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("new_user_automatically", "user", new User());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.POST)
    public ModelAndView AddUserAutomatically(@PathVariable int id, ModelMap model){

        //TODO Implement parsing linkedIn page with Selenium

        List<User> users = new ArrayList<>();
        User foundedUser1 = new User();
        User foundedUser2 = new User();
        Map<String, Object> users_map = new HashMap<String, Object>();
        users_map.put("events", users);

        return new ModelAndView("founded_user", users_map);
    }


    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model) {

        Map<String, Object> events_map = new HashMap<String, Object>();

    //TODO if in database
        List<Event> events = db.findById(id);
        events_map.put("events", events);

        return new ModelAndView("event", events_map);
    }

}
