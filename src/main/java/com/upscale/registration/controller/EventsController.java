package com.upscale.registration.controller;

import com.upscale.registration.model.Attendee;
import com.upscale.registration.model.Event;
import com.upscale.registration.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Controller
public class EventsController {

    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @Autowired
    private EventsRepository eventsRepository;

    @RequestMapping(value="/events/upcoming", method = RequestMethod.GET)
    public ModelAndView showUpcomingEventsPage(ModelMap model){

        Map<String, Object> eventsMap = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(false);
        eventsMap.put("events", events);

        return new ModelAndView("events/upcoming", eventsMap);
    }

    @RequestMapping(value="/events/passed", method = RequestMethod.GET)
    public ModelAndView showPassedEventsPage(ModelMap model){

        Map<String, Object> eventsMap = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(true);
        eventsMap.put("events", events);

        return new ModelAndView("events/passed", eventsMap);
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.GET)
    public ModelAndView showNewEventForm(){
        return new ModelAndView("events/new_event",
                                "event", new Event());
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.POST)
    public RedirectView submitForm(@ModelAttribute("event")Event newEvent, BindingResult result,
                                   ModelMap model){


        boolean eventNotInDatabase = eventsRepository.findByLinkedInLink(
                                        newEvent.getLinkedInLink()).isEmpty();
        String urlForRedirect;
        if (eventNotInDatabase){
            LocalDateTime now = LocalDateTime.now();
            if (newEvent.getDate().compareTo(convertToDateViaSqlTimestamp(now))==1){
                newEvent.setIsPassed(false);
                urlForRedirect = "/events/upcoming";
            } else {
                newEvent.setIsPassed(true);
                urlForRedirect = "/events/passed";
            }
            eventsRepository.save(newEvent);
        }else{
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Such Event has already exists in database");
        }
        return new RedirectView(urlForRedirect);
    }

    @RequestMapping(value="/events/statistics", method = RequestMethod.GET)
    public String showStatisticsPage(ModelMap model){
        // TODO Implement statistics
        return "statistics";
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
