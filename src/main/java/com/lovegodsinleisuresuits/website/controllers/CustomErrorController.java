package com.lovegodsinleisuresuits.website.controllers;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController extends BaseController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

		//Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		//Throwable rootCause = ExceptionUtils.getRootCause(exception);

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        model.addAttribute("status", status != null ? status.toString() : "???");
        model.addAttribute("message", message != null && !message.toString().isBlank() 
            ? message.toString() : "Something went wrong. But we will get right on it!");
        return "error";
    }

}
