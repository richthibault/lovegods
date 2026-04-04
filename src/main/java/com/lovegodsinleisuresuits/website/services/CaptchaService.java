package com.lovegodsinleisuresuits.website.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lovegodsinleisuresuits.website.exception.UserException;
import com.lovegodsinleisuresuits.website.utils.ServletUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Service
public class CaptchaService {

	// @Value("${google.project.id:}")
	// String googleProjectId;

	// @Value("${google.recaptcha.key.site:}")
	// String siteKey;

	@Value("${google.recaptcha.key.secret:}")
	String secretKey;

	private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

	Logger logger = LoggerFactory.getLogger(CaptchaService.class);

	private final RestClient restClient;
    
    public CaptchaService(RestClient restClient) {
        this.restClient = restClient;
    }

	public void validateRecaptchaResponse(String token, String recaptchaAction, HttpServletRequest request) throws IOException, UserException {

		if(!isValidRecaptchaResponse(token)) {
			throw new UserException("Captcha not provided.");
		}

		CaptchaResponse captchaResponse = restClient.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.host("www.google.com")
				.path("/recaptcha/api/siteverify")
				.queryParam("secret", secretKey)
				.queryParam("response", token)
				.queryParam("remoteip", ServletUtils.getClientIp(request))
				.build())
			.retrieve()
			.body(CaptchaResponse.class);

		if (captchaResponse == null || !captchaResponse.isSuccess()) {
			throw new UserException("Captcha response could not be processed, access denied.");
		}

		logger.info("Recaptcha3 score was {}", captchaResponse.getScore());
		
		// we're using recaptcha 3, so check the score
		if(captchaResponse.getScore()<0.5) {
			throw new UserException("Captcha failed, access denied.");
		}

	}

	private boolean isValidRecaptchaResponse(String response) {
		return StringUtils.isNotEmpty(response) && RESPONSE_PATTERN.matcher(response).matches();
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPropertyOrder({
		"success",
		"score",
		"challenge_ts",
		"hostname",
		"error-codes"
	})
	@Getter
	@Setter
	public static class CaptchaResponse {

		@JsonProperty("success")
		private boolean success;

		@JsonProperty("score")
		private float score;

		@JsonProperty("challenge_ts")
		private String challengeTs;

		@JsonProperty("hostname")
		private String hostname;

		@JsonProperty("error-codes")
		private ErrorCode[] errorCodes;

		@JsonIgnore
		public boolean hasClientError() {
			ErrorCode[] errors = getErrorCodes();
			if(errors == null) {
				return false;
			}
			for(ErrorCode error : errors) {
				switch(error) {
					case InvalidResponse:
					case MissingResponse:
					case InvalidSecret:
					case MissingSecret:
						return true;
				}
			}
			return false;
		}

		public boolean isSuccess() {
			return success && !hasClientError();
		}
	
		static enum ErrorCode {
			MissingSecret,     InvalidSecret,
			MissingResponse,   InvalidResponse;
	
			private static Map<String, ErrorCode> errorsMap = new HashMap<String, ErrorCode>(4);
	
			static {
				errorsMap.put("missing-input-secret",   MissingSecret);
				errorsMap.put("invalid-input-secret",   InvalidSecret);
				errorsMap.put("missing-input-response", MissingResponse);
				errorsMap.put("invalid-input-response", InvalidResponse);
			}
	
			@JsonCreator
			public static ErrorCode forValue(String value) {
				return errorsMap.get(value.toLowerCase());
			}
		}

	}

}
