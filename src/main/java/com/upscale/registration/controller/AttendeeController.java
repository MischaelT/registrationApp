package com.upscale.registration.controller;


import com.upscale.registration.model.Attendee;
import com.upscale.registration.model.Event;
import com.upscale.registration.parser.Parser;
import com.upscale.registration.repositories.AttendeeRepository;
import com.upscale.registration.repositories.EventsRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendeeController {
    @Autowired
    private AttendeeRepository attendeeRepository;

    @RequestMapping(value="/attendees/attendee/{id}", method = RequestMethod.GET)
    public ModelAndView showAttendee(@PathVariable long id, ModelMap model){

        ModelAndView view = null;

        try{
            Attendee attendee =  attendeeRepository.findById(id).get(0);
            view = new ModelAndView("attendees/attendee","attendee", attendee );
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
        return view;
    }

    @RequestMapping(value="/attendees/attendee/{id}", method = RequestMethod.POST)
    public ModelAndView updateAttendee(@PathVariable long id, ModelMap model){

        ModelAndView view = null;
        Attendee attendee;

        try {
            attendee =  attendeeRepository.findById(id).get(0);
            view = new ModelAndView("attendees/attendee","attendee", attendee );
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
        return view;
    }

    @RequestMapping(value="/attendees/attendee/{id}/update", method = RequestMethod.GET)
    public ModelAndView showAttendeeChangeInfoPage(@PathVariable long id, ModelMap model){

        ModelAndView view = null;

        try {
            Attendee attendee =  attendeeRepository.findById(id).get(0);
            view = new ModelAndView("attendees/update_attendee", "attendee", attendee);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
        return view;
    }

    @RequestMapping(value="/attendees/attendee/{id}/update", method = RequestMethod.POST)
    public RedirectView ChangeAttendeeInfo(@PathVariable long id, @ModelAttribute("attendee")Attendee attendee,
                                           BindingResult result, ModelMap model){

        RedirectView view = null;
        Attendee attendeeDb;
        try {
            attendeeDb = attendeeRepository.findById(id).get(0);
        } catch (Exception exception){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", exception);
        }
            attendeeDb.setName(attendee.getName());
            attendeeDb.setLinkedInLink(attendee.getLinkedInLink());
            attendeeRepository.save(attendeeDb);
            view = new RedirectView("/attendees/attendee/{id}");

        return view;
    }



}


