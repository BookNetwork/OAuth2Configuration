package com.sliit.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String  index() {
		return "index.html";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String  dashboard() {
		return "dashboard.html";
	}

}
