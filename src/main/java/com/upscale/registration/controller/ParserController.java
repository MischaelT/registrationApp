package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.forms.Form;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.model.User;
import com.upscale.registration.parser.Parser;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
import com.upscale.registration.repositories.UsersRepository;
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
public class ParserController {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private AttendeeRepository attendeeRepository;
    @Autowired
    private UsersRepository userRepository;

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                                     BindingResult result, ModelMap model){
        return new ModelAndView("attendees/new_attendee_automatically", "form_content", new Form());
    }

    @RequestMapping(value="/events/upcoming/{id}/new_attendee/automatically", method = RequestMethod.POST)
    public RedirectView AddAttendeeAutomatically(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                             BindingResult result, ModelMap model){

        Iterator<User> usersIterator = userRepository.findAll().iterator();
        User activeUser = usersIterator.next();
        Parser parser = new Parser(activeUser.getLinkedInLink(), activeUser.getLinkedPassword());

        String linked_link = form.getContent();
        List<Attendee> parsedAttendees = parser.runEventParsing(linked_link);

        HashSet<Event> events = new HashSet<>(eventsRepository.findById(id));

        for (Attendee attendee: parsedAttendees){
            attendee.setEvents(events);
            attendee.setFacebookLink("");
            attendeeRepository.save(attendee);
        }

        Event event = eventsRepository.findById(id).get(0);
        HashSet<Attendee> oldAttendees = new HashSet<>();
        oldAttendees.addAll(event.getAttendees());
        HashSet<Attendee> newAttendees = new HashSet<>(parsedAttendees);
        oldAttendees.addAll(newAttendees);
        event.setAttendees(oldAttendees);

        eventsRepository.save(event);

        return new RedirectView("/events/upcoming/{id}");
    }

    @RequestMapping(value="/attendees/attendee/{id}/change_info/automatically", method = RequestMethod.POST)
    public RedirectView collectAttendeeInfoAutomatically(@PathVariable long id, ModelMap model){

        RedirectView view = null;

        try{
            Attendee attendee = attendeeRepository.findById(id).get(0);

            Iterator<User> usersIterator = userRepository.findAll().iterator();
            User activeUser = usersIterator.next();
            Parser parser = new Parser(activeUser.getLinkedInLink(), activeUser.getLinkedPassword());

            Attendee newAttendee = parser.getAttendeeInformation(attendee);
            attendeeRepository.save(newAttendee);
            view = new RedirectView("/attendees/attendee/{id}");
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
        return view;
    }

    private Attendee httpPresent(String link, Attendee attendee){
        String[] splittedLink = link.split("//");
        boolean httpsPresent = splittedLink.length == 2;

        if (httpsPresent){
            attendee.setLinkedInLink(splittedLink[1]);
        }
        return attendee;
    }
}
