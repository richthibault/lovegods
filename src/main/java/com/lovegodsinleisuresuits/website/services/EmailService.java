package com.lovegodsinleisuresuits.website.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lovegodsinleisuresuits.website.model.ContactForm;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Value("${email.from.default}")
	String defaultFromEmail;

	@Value("${email.to.default}")
	String defaultToEmail;

	private final JavaMailSender mailSender;

	Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void submitContactForm(ContactForm contactForm) {

		StringBuilder emailText = new StringBuilder();
		emailText.append("Email: ").append(contactForm.email()).append("\n");
		emailText.append("Message:\n").append(contactForm.message()).append("\n");

		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
			helper.setFrom(defaultFromEmail);
			helper.setTo(defaultToEmail);
			helper.setSubject("LGILS: New Contact Form Submission");
			helper.setText(emailText.toString(), false);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("Error sending email to "+defaultToEmail, e);
		}

	}

}
