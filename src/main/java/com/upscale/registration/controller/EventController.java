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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.GET)
    public ModelAndView showUpcomingEvent(@PathVariable int id, ModelMap model){

        Map<String, Object> events_map = new HashMap<>();

        // TODO Implement mark_as_passed button
        // TODO Implement change_name button

        ModelAndView view ;

        if (eventsRepository.existsById(Long.valueOf(id))){
            List<Event> events =  eventsRepository.findById(id);
            events_map.put("events", events);
             view = new ModelAndView("event", events_map);
        } else{
             view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model) {

        Map<String, Object> events_map = new HashMap<>();

        ModelAndView view = null;

        if (eventsRepository.existsById(Long.valueOf(id))){
            List<Event> events =  eventsRepository.findById(id);
            events_map.put("events", events);
            view = new ModelAndView("event", events_map);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserManuallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/manually", method = RequestMethod.POST)
    public RedirectView addUserManually(@PathVariable int id, @ModelAttribute("user")User user, BindingResult result, ModelMap model){

        //TODO check the link format, presence or apsense of https

        RedirectView view = null;

        boolean userInDatabase = usersRepository.findByNameAndLinkedInLink(user.getName(), user.getLinkedInLink()).isEmpty();

        if (eventsRepository.existsById(Long.valueOf(id))&(userInDatabase)){

            List<Event> events = eventsRepository.findById(id);
            Event event = events.get(0);
            Set<User> user_list = event.getUsers();
            user_list.add(user);
            event.setUsers(user_list);
            eventsRepository.save(event);
            view = new RedirectView("/events/upcoming/{id}");
        } else{
            view = new RedirectView("/error");
        }
        return view;

    }
}
