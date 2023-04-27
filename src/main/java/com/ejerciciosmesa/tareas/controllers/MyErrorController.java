package com.ejerciciosmesa.tareas.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
       
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        model.addAttribute("errorCode",statusCode);
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {	  
	        	model.addAttribute("errorMessage1","Page Not Found");
	            return "errors/error";
	        } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	        	model.addAttribute("errorMessage1","You don't have permission for accessing this resource");
	        	return "errors/error";

	        } else {
	        	String errorMessage = HttpStatus.resolve(statusCode).toString().replace("_"," ");
	        	model.addAttribute("errorMessage1",errorMessage);
	        	return "errors/error";
	        }
	        
	    }
	   
	    model.addAttribute("errorCode",0);
	    model.addAttribute("errorMessage1","Unknown error");
        return "errors/error";
    }
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

