package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
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
        Event event;
        try {
            event =  eventsRepository.findById(id).get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        ModelAndView view = new ModelAndView("events/event", "event", event);
        return view;
    }

    @RequestMapping(value="/events/passed/{id}", method = RequestMethod.GET)
    public ModelAndView showPassedEvent(@PathVariable long id, ModelMap model) {
        Event event;
        try{
            event =  eventsRepository.findById(id).get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        ModelAndView view = new ModelAndView("events/event", "event", event);
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/manually", method = RequestMethod.GET)
    public ModelAndView showAddAttendeeManuallyForm(@PathVariable int id, ModelMap model){
        //since we use the same template new_attendee_manually.jsp in two different methods we pass
        // this two variables to generate info in jsp in accordance with the method we are generating jsp from
        model.addAttribute("fromEvent", true);
        model.addAttribute("postLink", "/events/upcoming/"+id+"/new_attendee/manually");
        return new ModelAndView("attendees/new_attendee_manually",
                                "attendee", new Attendee());

    }

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/manually", method = RequestMethod.POST)
    public RedirectView addAttendeeManually(@PathVariable int id, @ModelAttribute("attendee") Attendee newAttendee,
                                            BindingResult result, ModelMap model){

        Event event;
        try{
            List<Event> events = eventsRepository.findById(id);
            event = events.get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        Set<Attendee> attendeeList = event.getAttendees();
        boolean attendeeNotInDatabase = attendeeRepository.findByNameAndLinkedInLink(
                                        newAttendee.getName(), newAttendee.getLinkedInLink()).isEmpty();

        if (attendeeNotInDatabase){
            attendeeList.add(newAttendee);
        }else{
            Attendee attendeeDB = attendeeRepository.findByNameAndLinkedInLink(
                                  newAttendee.getName(), newAttendee.getLinkedInLink()).get(0);
            attendeeList.add(attendeeDB);
        }

        event.setAttendees(attendeeList);
        eventsRepository.save(event);

        RedirectView view = new RedirectView("/events/upcoming/{id}");
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.GET)
    public ModelAndView showChangeDataUpcomingPage(@PathVariable int id, ModelMap model){
        Event event;
        try{
            event =  eventsRepository.findById(id).get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        ModelAndView view = new ModelAndView("events/change_event_info", "event", event);
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.POST)
    public RedirectView changeDataUpcomingEvent(@PathVariable int id, @ModelAttribute("event")Event event,
                                                BindingResult result, ModelMap model){
        Event eventDb;
        try {
            eventDb = eventsRepository.findById(id).get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        eventDb.setIsPassed(event.getIsPassed());
        eventDb.setDate(event.getDate());
        eventDb.setLinkedInLink(event.getLinkedInLink());
        eventDb.setName(event.getName());
        eventsRepository.save(eventDb);

        RedirectView view = new RedirectView("/events/upcoming/{id}");
        return view;
    }
}
