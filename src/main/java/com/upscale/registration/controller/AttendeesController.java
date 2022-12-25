package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value="/attendees", method = RequestMethod.GET)
    public ModelAndView showAttendees(ModelMap model){

        Map<String, Object> attendeesMap = new HashMap<>();
        List<Attendee> attendees = (List<Attendee>) attendeeRepository.findAll();
        attendeesMap.put("attendees", attendees);

        return new ModelAndView("attendees/attendees", attendeesMap);
    }

    @RequestMapping(value="/attendees/new_attendee/manually", method = RequestMethod.GET)
    public ModelAndView showAddAttendeePage(Model model){

        Map<String, Object> eventMap = new HashMap<>();

        List<Event> events = eventsRepository.findByIsPassed(false);
        model.addAttribute("upcoming_events", events);

        model.addAttribute("fromEvent", false);

        ModelAndView modelAndView = new ModelAndView("attendees/new_attendee_manually", "attendee", new Attendee());

        return modelAndView;
    }

    @RequestMapping(value="/attendees/new_attendee/manually", method = RequestMethod.POST)
    public String addAttendeeManually(@ModelAttribute("attendee") Attendee attendee, BindingResult result, ModelMap model){
        attendeeRepository.save(attendee);
        return "success";
    }
}
