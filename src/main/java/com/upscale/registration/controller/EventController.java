package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO solve a problem with navbar
@Controller
public class EventController {

    @Autowired
    private EventsRepository db;

    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.GET)
    public ModelAndView showUpcomingEvent(@PathVariable int id, ModelMap model){

        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = (List<Event>) db.findById(id);
        events_map.put("events", events);

        return new ModelAndView("event", events_map);
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserManuallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.POST)
    public ModelAndView addUserManually(@PathVariable int id, ModelMap model){

        //TODO implement adding user to the database

        List<Event> events = (List<Event>) db.findById(id);
        Set<User> user_list = events.get(0).getUsers();
        Map<String, Object> events_map = new HashMap<String, Object>();
        events_map.put("events", events);

        return new ModelAndView("success", events_map);
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, ModelMap model){
        // TODO implement form to paste the linkedIn link to parse
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.POST)
    public ModelAndView AddUserAutomatically(@PathVariable int id, ModelMap model){

        //TODO Implement parsing linkedIn page with Selenium

        List<Event> events = (List<Event>) db.findById(id);
        Set<User> user_list = events.get(0).getUsers();
        Map<String, Object> events_map = new HashMap<String, Object>();
        events_map.put("events", events);

        return new ModelAndView("success", events_map);
    }


    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model){

        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = (List<Event>) db.findById(id);
        events_map.put("events", events);

        return new ModelAndView("event", events_map);
    }




}
