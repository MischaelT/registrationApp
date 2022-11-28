package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class EventsController {

    @Autowired
    private EventsRepository db;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model){
        return "welcome";
    }

    @RequestMapping(value="/events/upcoming", method = RequestMethod.GET)
    public ModelAndView showUpcomingEventsPage(ModelMap model){

        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = (List<Event>) db.findByIsPassed(false);
        events_map.put("events", events);

        return new ModelAndView("upcoming", events_map);
    }

    @RequestMapping(value="/events/passed", method = RequestMethod.GET)
    public ModelAndView showPassedEventsPage(ModelMap model){

        Map<String, Object> events_map = new HashMap<String, Object>();
        List<Event> events = (List<Event>) db.findByIsPassed(true);
        events_map.put("events", events);

        return new ModelAndView("passed", events_map);
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.GET)
    public ModelAndView showNewEventForm(){
        return new ModelAndView("new_event", "event", new Event());
    }

    @RequestMapping(value="/events/new_event", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("event")Event event, BindingResult result, ModelMap model){
        // check the date. If it is bigger than today, set isPassed to false
        event.setIsPassed(false);
        db.save(event);
        return "success";
    }

    @RequestMapping(value="/events/statistics", method = RequestMethod.GET)
    public String showStatisticsPage(ModelMap model){
        return "statistics";
    }
}
