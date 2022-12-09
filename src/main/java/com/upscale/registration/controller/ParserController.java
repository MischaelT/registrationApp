package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Form;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.parser.Parser;
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

import java.util.HashSet;
import java.util.List;

@Controller
public class ParserController {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private AttendeeRepository attendeeRepository;

    @RequestMapping(value="/events/upcoming/{id}/add_attendee/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                                     BindingResult result, ModelMap model){
        return new ModelAndView("new_user_automatically", "form_content", new Form());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_attendee/automatically", method = RequestMethod.POST)
    public RedirectView AddUserAutomatically(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                             BindingResult result, ModelMap model){

        Parser parser = new Parser("m.kouzin@upscale-labs.com","VeryStrongPassword");
        String linked_link = form.getContent();
        List<Attendee> parsedAttendees = parser.runParsing(linked_link);

        HashSet<Event> events = new HashSet<>(eventsRepository.findById(id));

        for (Attendee user: parsedAttendees){
            user.setEvents(events);
            user.setFacebookLink("");
            attendeeRepository.save(user);
        }

        Event event = eventsRepository.findById(id).get(0);
        HashSet<Attendee> oldUsers = new HashSet<>();
        oldUsers.addAll(event.getAttendees());
        HashSet<Attendee> newUsers = new HashSet<>(parsedAttendees);
        oldUsers.addAll(newUsers);
        event.setAttendees(oldUsers);

        eventsRepository.save(event);

        return new RedirectView("/events/upcoming/{id}");
    }
}
