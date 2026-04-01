package com.lovegodsinleisuresuits.website.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bio")
public class BioController extends BaseController {

	private static final List<Extension> EXTENSIONS = List.of(ImageAttributesExtension.create());

	private static final Parser PARSER = Parser.builder()
			.extensions(EXTENSIONS)
			.build();

	private static final HtmlRenderer RENDERER = HtmlRenderer.builder()
			.extensions(EXTENSIONS)
			.build();

	@GetMapping
	public String bio(Model model) {
		String html = "";
		try {
			ClassPathResource resource = new ClassPathResource("docs/lovegods-bio.md");
			String markdown = resource.getContentAsString(StandardCharsets.UTF_8);
			Node document = PARSER.parse(markdown);
			html = RENDERER.render(document);
		} catch (IOException e) {
			html = "<p>Bio coming soon.</p>";
		}
		model.addAttribute("bioHtml", html);
		return "bio";
	}

}
