package com.upscale.registration.controller;


import com.upscale.registration.model.Event;
import com.upscale.registration.model.User;
import com.upscale.registration.repositories.UsersRepository;
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
public class UserController {
    @Autowired
    private UsersRepository userRepository;

    @RequestMapping(value="/users/user/{id}", method = RequestMethod.GET)
    public ModelAndView showUser(@PathVariable long id, ModelMap model){

        Map<String, Object> users_map = new HashMap<>();
        ModelAndView view = null;

        if (userRepository.existsById(Long.valueOf(id))){
            List<User> users =  userRepository.findById(id);
            users_map.put("users", users);
            view = new ModelAndView("users", users_map);
        } else{
            view = new ModelAndView("error");
        }
        return view;
    }

    @RequestMapping(value="/users/user/{id}", method = RequestMethod.POST)
    public ModelAndView updateUser(@PathVariable long id, ModelMap model){

        Map<String, Object> users_map = new HashMap<>();
        ModelAndView view = null;

        if (userRepository.existsById(Long.valueOf(id))){
            List<User> users =  userRepository.findById(id);
            users_map.put("users", users);
            view = new ModelAndView("users", users_map);
        } else{
            view = new ModelAndView("error");
        }
        return view;

    }

    @RequestMapping(value="/user/{id}/statistics", method = RequestMethod.GET)
    public String showStatisticsPage(@PathVariable long id, ModelMap model){

        // TODO Implement statistics by user

        return "statistics";
    }
}


