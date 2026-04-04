package com.lovegodsinleisuresuits.website.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {

	@Value("${google.analytics.id:}")
	String googleAnalyticsId;

	@Value("${google.recaptcha.key.site:}")
	String googleRecaptchaSiteKey;

	@ModelAttribute("googleAnalyticsId")
	public String getGoogleAnalyticsId() {
		return googleAnalyticsId;
	}

	@ModelAttribute("googleRecaptchaSiteKey")
	public String getGoogleRecaptchaSiteKey() {
		return googleRecaptchaSiteKey;
	}

}