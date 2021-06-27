package com.itscout.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/admin")
	public String showAdmin() {
		return "admin";
	}
}
