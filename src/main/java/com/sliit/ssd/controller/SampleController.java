package com.sliit.ssd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SampleController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String  sample() {
		return "index.html";
	}

}
