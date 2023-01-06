package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Attendee;
import com.upscale.registration.model.EventAttendee;
import com.upscale.registration.parser.Parser;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.AttendeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import java.util.stream.Collectors;

@Controller
public class EventController {

    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private AttendeeRepository attendeeRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.GET)
    public ModelAndView showUpcomingEvent(@PathVariable int id, ModelMap model){
        List<Object[]> attended = entityManager.createQuery(
                        "SELECT attendeeId FROM EventAttendee WHERE isAttended=?1 AND eventId=?2")
                        .setParameter(1, true).setParameter(2, id)
                        .getResultList();

        model.addAttribute("attended", attended);

        Event event;
        try {
            event =  eventsRepository.findById(id).get(0);
        } catch (NullPointerException exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        ModelAndView view = new ModelAndView("events/event", "event", event);
        return view;
    }

    @Transactional
    @RequestMapping(value="/events/upcoming/{id}", method = RequestMethod.POST)
    public ModelAndView processChosenUsersEvent(@PathVariable int id, ModelMap model,
                                                @RequestParam("attendees") String attendees) {

        List<String> splittedString = Arrays.asList(attendees.split(","));
        List<Long> chosenAttendeesIds = new ArrayList<>();
        try {
            chosenAttendeesIds = splittedString.stream().map(str->Long.parseLong(str))
                                .collect(Collectors.toList());
        }catch (NumberFormatException exc){
        }

        List<Long> notChosenAttendeesIds = entityManager.createQuery(
                    "SELECT attendeeId FROM EventAttendee WHERE eventId=?1")
                    .setParameter(1, id).getResultList();
        notChosenAttendeesIds.removeAll(chosenAttendeesIds);

        for (Long attendeeId: chosenAttendeesIds) {
            entityManager.createQuery(
                    "UPDATE EventAttendee SET isAttended=?1 WHERE attendeeId=?2")
                    .setParameter(1, true).setParameter(2,attendeeId).executeUpdate();
        }

        for (Long attendeeId: notChosenAttendeesIds) {
            entityManager.createQuery(
                            "UPDATE EventAttendee SET isAttended=?1 WHERE attendeeId=?2")
                    .setParameter(1, false).setParameter(2,attendeeId).executeUpdate();
        }

        List<Object[]> attended = entityManager.createQuery(
                        "SELECT attendeeId FROM EventAttendee WHERE isAttended=?1 AND eventId=?2")
                        .setParameter(1, true).setParameter(2, id)
                        .getResultList();

        model.addAttribute("attended", attended);

        Event event;
        try {
            event =  eventsRepository.findById(id).get(0);
        } catch (NullPointerException exception){
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
        } catch (NullPointerException exception){
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
        } catch (NullPointerException exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        RedirectView view;
        if (!event.getIsPassed()){
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
            view = new RedirectView("/events/upcoming/{id}");

        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found in upcoming");
        }

        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.GET)
    public ModelAndView showChangeDataUpcomingPage(@PathVariable int id, ModelMap model){
        Event event;
        try{
            event =  eventsRepository.findById(id).get(0);
        } catch (NullPointerException exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        ModelAndView view;
        if (!event.getIsPassed()){
            view = new ModelAndView("events/change_event_info", "event", event);
        }else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found in upcomingh");
        }
        return view;
    }

    @RequestMapping(value="/events/upcoming/{id}/change_data", method = RequestMethod.POST)
    public RedirectView changeDataUpcomingEvent(@PathVariable int id, @ModelAttribute("event")Event event,
                                                BindingResult result, ModelMap model){
        Event eventDb;
        try {
            eventDb = eventsRepository.findById(id).get(0);
        } catch (NullPointerException exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Such Event Not Found", exception);
        }
        if (!eventDb.getIsPassed()) {
            eventDb.setIsPassed(event.getIsPassed());
            eventDb.setDate(event.getDate());
            eventDb.setLinkedInLink(event.getLinkedInLink());
            eventDb.setName(event.getName());
            eventsRepository.save(eventDb);
        }

        String redirectLink;

        if (eventDb.getIsPassed()){
            redirectLink ="/events/passed/{id}";
        }else{
            redirectLink = "/events/upcoming/{id}";
        }

        RedirectView view = new RedirectView(redirectLink);
        return view;
    }
}
