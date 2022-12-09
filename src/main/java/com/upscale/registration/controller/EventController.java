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
    private AttendeeRepository attendeeRepository;

    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.GET)
    public ModelAndView showUpcomingEvent(@PathVariable int id, ModelMap model){

        Map<String, Object> eventsMap = new HashMap<>();

        ModelAndView view ;

        if (eventsRepository.existsById(Long.valueOf(id))){
            List<Event> events =  eventsRepository.findById(id);
            eventsMap.put("events", events);
             view = new ModelAndView("event", eventsMap);
        } else{
             view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model) {

        Map<String, Object> eventsMap = new HashMap<>();

        ModelAndView view = null;

        if (eventsRepository.existsById(Long.valueOf(id))){
            List<Event> events =  eventsRepository.findById(id);
            eventsMap.put("events", events);
            view = new ModelAndView("event", eventsMap);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/add_attendee/manually", method = RequestMethod.GET)
    public ModelAndView showAddUserManuallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("new_user", "user", new Attendee());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_attendee/manually", method = RequestMethod.POST)
    public RedirectView addUserManually(@PathVariable int id, @ModelAttribute("user") Attendee user, BindingResult result, ModelMap model){

        String link = user.getLinkedInLink();
        String[] splittedLink = link.split("//");
        boolean httpsPresent = splittedLink.length == 2;

        if (httpsPresent){
            user.setLinkedInLink(splittedLink[1]);
        }

        RedirectView view = null;

        boolean userNotInDatabase = attendeeRepository.findByNameAndLinkedInLink(user.getName(), user.getLinkedInLink()).isEmpty();

        if (eventsRepository.existsById(Long.valueOf(id))&(userNotInDatabase)){

            List<Event> events = eventsRepository.findById(id);
            Event event = events.get(0);
            Set<Attendee> attendeeList = event.getAttendees();
            attendeeList.add(user);
            event.setAttendees(attendeeList);
            eventsRepository.save(event);
            view = new RedirectView("/events/upcoming/{id}");
        } else{
            view = new RedirectView("/error");
        }
        return view;

    }
}
