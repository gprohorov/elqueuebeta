package com.med.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MyCustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public void handleError(HttpServletResponse response) throws Exception {
    	response.sendRedirect("/");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}