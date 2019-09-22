package com.sliit.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainRouterController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String  index() {
		return "index.html";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String  dashboard() {
		return "dashboard.html";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String  googleLogin() {
		return "dashboard.html";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public String  logout() {
		System.out.println("Logout button pressed");
		
		return "<h1>Logout</h1>";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String  upload() {
		return "File Upload";
	}

}
