package com.lovegodsinleisuresuits.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lovegodsinleisuresuits.website.model.ContactForm;
import com.lovegodsinleisuresuits.website.services.CaptchaService;
import com.lovegodsinleisuresuits.website.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contact")
public class ContactController extends BaseController {

	private final EmailService emailService;
	private final CaptchaService captchaService;

	private final static String RECAPTCHA_ACTION = "contact_form_submit";

	public ContactController(EmailService emailService, CaptchaService captchaService) {
		this.emailService = emailService;
		this.captchaService = captchaService;
	}

	private record ContactResponse(String status, String message) {
	}

	@GetMapping
	public String get(ModelMap model) {

		ContactForm contactForm = new ContactForm("", "");
		model.addAttribute("contactForm", contactForm);

		return "contact";
	}

	@PostMapping
	public @ResponseBody ContactResponse postForm(@ModelAttribute ContactForm contactForm, ModelMap model,
			HttpServletRequest request, BindingResult result,
			final RedirectAttributes redirectAttributes) throws Exception {

		if (result.hasErrors()) {
			return new ContactResponse("error", "The following errors occurred: " + result.getAllErrors().toString());
		}
		
		String captchaResponse = request.getParameter("g-recaptcha-response");
		try {
        	captchaService.validateRecaptchaResponse(captchaResponse, RECAPTCHA_ACTION, request);
		} catch (Exception e) {
			return new ContactResponse("error", e.getMessage());
		}
		
		emailService.submitContactForm(contactForm);
		
		return new ContactResponse("success", "Contact form submitted successfully");

	}

}
