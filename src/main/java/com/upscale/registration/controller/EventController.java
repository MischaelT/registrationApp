package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Form;
import com.upscale.registration.model.User;
import com.upscale.registration.parser.Parser;
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
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventsRepository db;
    @Autowired
    private UserRepository userDb;

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
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                                     BindingResult result, ModelMap model){


        return new ModelAndView("new_user_automatically", "form_content", new Form());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.POST)
    public RedirectView AddUserAutomatically(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                             BindingResult result, ModelMap model){

        Parser parser = new Parser("m.kouzin@upscale-labs.com","VeryStrongPassword");
        String linked_link = form.getContent();
        List<User> new_users = parser.runParsing(linked_link);
        //TODO bug with dissapearing users
        HashSet<Event> events = new HashSet<>(db.findById(id));

        for (User user: new_users){
            user.setEvents(events);
            user.setFacebookLink("");
            userDb.save(user);
        }

        Event event = db.findById(id).get(0);

        HashSet<User> users = new HashSet<>(new_users);
        event.setUsers(users);
        db.save(event);

        return new RedirectView("/events/upcoming/{id}");
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
