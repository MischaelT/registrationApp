package com.upscale.registration.controller;

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

        List<Event> events = eventsRepository.findByIsPassed(false);

        model.addAttribute("upcoming_events", events);
        //since we use the same template new_attendee_manually.jsp in two different methods we pass
        // this two variables to generate info in jsp in accordance with the method we are generating jsp from
        model.addAttribute("fromEvent", false);
        model.addAttribute("postLink", "/attendees/new_attendee/manually");

        ModelAndView modelAndView = new ModelAndView("attendees/new_attendee_manually",
                                                     "attendee", new Attendee());
        return modelAndView;
    }

    @RequestMapping(value="/attendees/new_attendee/manually", method = RequestMethod.POST)
    public RedirectView addAttendeeManually(@ModelAttribute("attendee") Attendee newAttendee,
                                            BindingResult result, ModelMap model){
        Event chosenEvent = (Event) newAttendee.getEvents().toArray()[0];
        try{
            List<Event> events = eventsRepository.findById(chosenEvent.getId());
            events.get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        Set<Attendee> attendeeList = chosenEvent.getAttendees();

        boolean attendeeNotInDatabase = attendeeRepository.findByNameAndLinkedInLink(
                newAttendee.getName(), newAttendee.getLinkedInLink()).isEmpty();

        if (attendeeNotInDatabase){
            attendeeList.add(newAttendee);
        }else {
            Attendee attendeeDB = attendeeRepository.findByNameAndLinkedInLink(
                    newAttendee.getName(), newAttendee.getLinkedInLink()).get(0);
            attendeeList.add(attendeeDB);
        }
        chosenEvent.setAttendees(attendeeList);
        eventsRepository.save(chosenEvent);
        return new RedirectView("/attendees");
    }
}
