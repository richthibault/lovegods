package com.lovegodsinleisuresuits.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bio")
public class BioController extends BaseController {

	@GetMapping
	public String bio() {
		return "bio";
	}

}
