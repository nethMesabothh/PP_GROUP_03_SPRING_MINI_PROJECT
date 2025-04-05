package com.both.gamified_habit_tracker_api.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(toEmail);
		helper.setSubject("Verify Your Account");
		helper.setText(buildEmailContent(otp), true);

		mailSender.send(message);
	}

	private String buildEmailContent(String otp) {
		Context context = new Context();
		context.setVariable("otp", otp);
		return templateEngine.process("email-template", context);
	}
}
