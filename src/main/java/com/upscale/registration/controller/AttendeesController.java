package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
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
public class AttendeesController {

    @Autowired
    private AttendeeRepository attendeeRepository;
    @Autowired
    private EventsRepository eventsRepository;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ModelAndView showUsers(ModelMap model){

        Map<String, Object> attendeesMap = new HashMap<>();
        List<Attendee> attendees = (List<Attendee>) attendeeRepository.findAll();
        attendeesMap.put("attendees", attendees);

        return new ModelAndView("attendees", attendeesMap);
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserPage(){

        Map<String, Object> EventMap = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(false);
        EventMap.put("upcoming_events", events);

        //TODO implement transferring list of events to the view

        ModelAndView modelAndView = new ModelAndView("new_user", "user", new Attendee());
        modelAndView.addObject("upcoming_events", EventMap);

        return modelAndView;
    }

    @RequestMapping(value="/users/new_user/manually", method = RequestMethod.POST)
    public String addUserManually(@ModelAttribute("user") Attendee attendee, BindingResult result, ModelMap model){

        attendeeRepository.save(attendee);

        return "success";
    }
}
