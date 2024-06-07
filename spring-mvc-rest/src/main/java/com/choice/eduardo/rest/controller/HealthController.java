package com.choice.eduardo.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {

    @RequestMapping(value = "/health-check",method = {RequestMethod.GET})
    public @ResponseBody String checkHealth(){
        //TODO: impl a health check for the application
        return "pin";
    }
}
