package com.upscale.registration.controller;


import com.upscale.registration.model.Attendee;
import com.upscale.registration.repositories.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendeeController {
    @Autowired
    private AttendeeRepository attendeeRepository;

    @RequestMapping(value="/users/user/{id}", method = RequestMethod.GET)
    public ModelAndView showUser(@PathVariable long id, ModelMap model){

        Map<String, Object> attendeesMap = new HashMap<>();
        ModelAndView view = null;

        if (attendeeRepository.existsById(Long.valueOf(id))){
            List<Attendee> attendees =  attendeeRepository.findById(id);
            attendeesMap.put("attendees", attendees);
            view = new ModelAndView("user", attendeesMap);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/users/user/{id}", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable long id, ModelMap model){

        Map<String, Object> attendeesMap = new HashMap<>();
        ModelAndView view = null;

        if (attendeeRepository.existsById(Long.valueOf(id))){
            List<Attendee> attendees =  attendeeRepository.findById(id);
            attendeesMap.put("attendees", attendees);
            view = new ModelAndView("user", attendeesMap);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/user/{id}/statistics", method = RequestMethod.GET)
    public String showStatisticsPage(@PathVariable long id, ModelMap model){

        return "statistics";
    }
}


