package com.upscale.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model){
        return "welcome";
    }

    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String showErrorPage(ModelMap model){
        return "ooops";
    }
}
