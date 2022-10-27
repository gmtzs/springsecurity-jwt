package com.utils.ssecurityjwt.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ApplicationController {

	
	@GetMapping("/hello")
	public String helloWorld() {
		log.info("Saludando");
		return "Hello World";
	}
}
