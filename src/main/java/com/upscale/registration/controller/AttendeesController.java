package com.upscale.registration.controller;

import com.upscale.registration.forms.Form;
import com.upscale.registration.model.Event;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        model.addAttribute("postLink", "/attendees/new_attendee/manually");

        ModelAndView modelAndView = new ModelAndView("attendees/new_attendee_manually", "attendee", new Attendee());

        return modelAndView;
    }

    @RequestMapping(value="/attendees/new_attendee/manually", method = RequestMethod.POST)
    public RedirectView addAttendeeManually(@ModelAttribute("attendee") Attendee attendee, BindingResult result, ModelMap model){

        // TODO Check if attendee is already in database
        Event chosenEvent = (Event) attendee.getEvents().toArray()[0];

        try{
            List<Event> events = eventsRepository.findById(chosenEvent.getId());
            events.get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        Set<Attendee> attendeeList = chosenEvent.getAttendees();
        attendeeList.add(attendee);
        chosenEvent.setAttendees(attendeeList);

        eventsRepository.save(chosenEvent);
//        attendeeRepository.save(attendee);
        return new RedirectView("/attendees");
    }
}
