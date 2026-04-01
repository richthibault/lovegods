package com.lovegodsinleisuresuits.website.exception;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends Exception {

	private static final long serialVersionUID = -4721828101058346514L;

	private String htmlMessage;
	private String htmlTitle;

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, String htmlMessage) {
		super(message);
		this.htmlMessage = htmlMessage;
	}

	public UserException(String message, String htmlMessage, String htmlTitle) {
		super(message);
		this.htmlMessage = htmlMessage;
		this.htmlTitle = htmlTitle;
	}

	public String getHtmlMessage() {
		return StringUtils.isEmpty(htmlMessage) ? getMessage() : htmlMessage;
	}

}
