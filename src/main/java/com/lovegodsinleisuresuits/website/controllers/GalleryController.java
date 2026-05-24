package com.lovegodsinleisuresuits.website.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lovegodsinleisuresuits.website.model.GalleryImage;

@Controller
@RequestMapping("/gallery")
public class GalleryController extends BaseController {

	private final ApplicationContext applicationContext;

	public GalleryController(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@GetMapping
	public String gallery(Model model) throws IOException {
		model.addAttribute("reunionImages", loadImages("reunion"));
		model.addAttribute("classicImages", loadImages("classic"));
		return "gallery";
	}

	private List<GalleryImage> loadImages(String folder) throws IOException {
		Resource[] resources = applicationContext.getResources("classpath:static/img/gallery/" + folder + "/*.*");
		return Arrays.stream(resources)
				.map(Resource::getFilename)
				.filter(name -> name != null && name.matches("(?i).*\\.(jpg|jpeg|png|gif|webp)$"))
				.sorted()
				.map(name -> new GalleryImage(
						"/img/gallery/" + folder + "/" + name,
						"/img/gallery/" + folder + "/thumbs/" + name,
						toAltText(name)))
				.toList();
	}

	private String toAltText(String filename) {
		return filename
				.replaceAll("(?i)\\.[^.]+$", "")
				.replace("-", " ")
				.replace("_", " ");
	}

}
