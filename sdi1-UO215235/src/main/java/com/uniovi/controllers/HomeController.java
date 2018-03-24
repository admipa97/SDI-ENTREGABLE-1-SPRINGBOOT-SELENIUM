package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.uniovi.services.LoggerService;

@Controller
public class HomeController {

	private LoggerService loggerService = new LoggerService(this);

	@GetMapping("/")
	public String index() {
		loggerService.info("Entrada del usuario en la aplicación");
		return "index";
	}

}
