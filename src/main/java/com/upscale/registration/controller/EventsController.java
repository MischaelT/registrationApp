package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

        Map<String, Object> events_map = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(false);
        events_map.put("events", events);

        return new ModelAndView("upcoming", events_map);
    }

    @RequestMapping(value="/events/passed", method = RequestMethod.GET)
    public ModelAndView showPassedEventsPage(ModelMap model){

        Map<String, Object> events_map = new HashMap<>();
        List<Event> events = eventsRepository.findByIsPassed(true);
        events_map.put("events", events);

        return new ModelAndView("passed", events_map);
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.GET)
    public ModelAndView showNewEventForm(){
        return new ModelAndView("new_event", "event", new Event());
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.POST)
    public RedirectView submitForm(@ModelAttribute("event")Event event, BindingResult result, ModelMap model){

        LocalDateTime now = LocalDateTime.now();
        String urlForRedirect = "";

        if (event.getDate().compareTo(convertToDateViaSqlTimestamp(now))==1){
            event.setIsPassed(false);
            urlForRedirect = "/events/upcoming";
        } else {
            event.setIsPassed(true);
            urlForRedirect = "/events/passed";
        }
        eventsRepository.save(event);

        return new RedirectView(urlForRedirect);
    }

    @RequestMapping(value="/events/statistics", method = RequestMethod.GET)
    public String showStatisticsPage(ModelMap model){

        // TODO implement statistics by event

        return "statistics";
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
