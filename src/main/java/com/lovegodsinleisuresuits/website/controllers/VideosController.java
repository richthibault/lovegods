package com.lovegodsinleisuresuits.website.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lovegodsinleisuresuits.website.model.Video;

@Controller
@RequestMapping("/videos")
public class VideosController extends BaseController {

	@GetMapping
	public String videos(ModelMap model) {

		List<Video> videos = List.of(

			new Video("Lovegods in Leisure Suits @ Decades 1989", 
				"https://www.youtube.com/watch?v=yIqupDBp700"),

			new Video("Lovegods in Leisure Suits - Jammy Awards 1990", 
				"https://www.youtube.com/watch?v=KH0baEisLns"),

			new Video("Lovegods in Leisure Suits at JJ Whispers - Orlando FL", 
				"https://www.youtube.com/watch?v=UV8nBFYP1IU"),

			new Video("Lovegods in Leisure Suits - AMPTV", 
				"https://www.youtube.com/watch?v=pgOkWjvSIHo"),

			new Video("Michael Bales' Orlando Story: Lovegods in Leisure Suits, Rocket 88 & More", 
				"https://www.youtube.com/watch?v=7egPCTFUcCo")
		);

		// Add the list of videos to the model
		model.addAttribute("videos", videos);

		return "videos";
	}

}
