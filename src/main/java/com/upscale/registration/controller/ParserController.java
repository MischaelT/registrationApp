package com.upscale.registration.controller;

import com.upscale.registration.model.Event;
import com.upscale.registration.model.Form;
import com.upscale.registration.model.User;
import com.upscale.registration.parser.Parser;
import com.upscale.registration.repositories.EventsRepository;
import com.upscale.registration.repositories.UsersRepository;
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
    private UsersRepository usersRepository;

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.GET)
    public ModelAndView showAddUserAutomaticallyForm(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                                     BindingResult result, ModelMap model){
        return new ModelAndView("new_user_automatically", "form_content", new Form());
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically", method = RequestMethod.POST)
    public RedirectView AddUserAutomatically(@PathVariable int id, @ModelAttribute("form_content") Form form,
                                             BindingResult result, ModelMap model){

        Parser parser = new Parser("m.kouzin@upscale-labs.com","VeryStrongPassword");
        String linked_link = form.getContent();
        List<User> parsedUsers = parser.runParsing(linked_link);

        HashSet<Event> events = new HashSet<>(eventsRepository.findById(id));

        for (User user: parsedUsers){
            user.setEvents(events);
            user.setFacebookLink("");
            usersRepository.save(user);
        }

        Event event = eventsRepository.findById(id).get(0);
        HashSet<User> oldUsers = new HashSet<>();
        oldUsers.addAll(event.getUsers());
        HashSet<User> newUsers = new HashSet<>(parsedUsers);
        oldUsers.addAll(newUsers);
        event.setUsers(oldUsers);

        eventsRepository.save(event);

        return new RedirectView("/events/upcoming/{id}");
    }

    @RequestMapping(value="/events/upcoming/{id}/add_user/automatically/user_list", method = RequestMethod.POST)
    public String addNewUsersToDb(ModelMap model){

        // TODO Implement adding user to DB

        return "success";
    }
}
