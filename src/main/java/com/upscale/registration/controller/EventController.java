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
        ModelAndView view ;
        if (eventsRepository.existsById(Long.valueOf(id))){
            Event event =  eventsRepository.findById(id).get(0);
             view = new ModelAndView("events/event", "event", event);
        } else{
             view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model) {
        ModelAndView view = null;
        if (eventsRepository.existsById(Long.valueOf(id))){
            Event event =  eventsRepository.findById(id).get(0);
            view = new ModelAndView("events/event", "event", event);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/manually", method = RequestMethod.GET)
    public ModelAndView showAddAttendeeManuallyForm(@PathVariable int id, ModelMap model){
        return new ModelAndView("attendees/new_attendee_manually", "attendee", new Attendee());
    }

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/manually", method = RequestMethod.POST)
    public RedirectView addAttendeeManually(@PathVariable int id, @ModelAttribute("attendee") Attendee attendee, BindingResult result, ModelMap model){

        String link = attendee.getLinkedInLink();


        RedirectView view = null;

        boolean attendeeNotInDatabase = attendeeRepository.findByNameAndLinkedInLink(attendee.getName(), attendee.getLinkedInLink()).isEmpty();

        if (eventsRepository.existsById(Long.valueOf(id))&(attendeeNotInDatabase)){

            List<Event> events = eventsRepository.findById(id);
            Event event = events.get(0);
            Set<Attendee> attendeeList = event.getAttendees();
            attendeeList.add(attendee);
            event.setAttendees(attendeeList);
            eventsRepository.save(event);
            view = new RedirectView("/events/upcoming/{id}");
        } else{
            view = new RedirectView("/error");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.GET)
    public ModelAndView showChangeDataUpcomingPage(@PathVariable int id, ModelMap model){

        ModelAndView view;

        if (eventsRepository.existsById(Long.valueOf(id))){
            Event event =  eventsRepository.findById(id).get(0);
            view = new ModelAndView("events/change_event_info", "event", event);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.POST)
    public RedirectView changeDataUpcomingEvent(@PathVariable int id, @ModelAttribute("event")Event event,
                                                BindingResult result, ModelMap model){
        RedirectView view;
        if (eventsRepository.existsById(Long.valueOf(id))){
            Event eventDb = eventsRepository.findById(id).get(0);
            eventDb.setIsPassed(event.getIsPassed());
            eventDb.setDate(event.getDate());
            eventDb.setLinkedInLink(event.getLinkedInLink());
            eventDb.setName(event.getName());
            eventsRepository.save(eventDb);
            view = new RedirectView("/events/upcoming/{id}");
            } else{
            view = new RedirectView("error");
        }
        return view;
    }

}
