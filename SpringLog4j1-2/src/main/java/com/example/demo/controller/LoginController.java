package com.example.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.service.LoginService;


@Controller
@SessionAttributes("name")
public class LoginController {
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	@Autowired
    LoginService service;
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "login";
    }
    @RequestMapping(value="/login", method = RequestMethod.POST)
    
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password)
    {
        boolean isValidUser = service.validateUser(name, password);
        
        logger.info("name is"+name);
        logger.info("the password is "+password);        
       
        if (!isValidUser) {
        	
        	logger.info("name is"+name);
            logger.info("the password is "+password);        	
            model.put("errorMessage", "Invalid Credentials");
      
            return "login";
        }
        	
       
        model.put("name", name);
        model.put("password", password);
        return "welcome";
    }


}
