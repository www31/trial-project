package com.lps.ldtracker.serviceImpl;

import static com.lps.ldtracker.constants.LdTrackerConstants.EMAIL_TEMPLATE;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_MAIL;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_MAIL_MSG;
import static com.lps.ldtracker.constants.LdTrackerConstants.NEW_USER_ACCOUNT_VERIFICATION;
import static com.lps.ldtracker.constants.LdTrackerConstants.UTF_8_ENCODING;
import static com.lps.ldtracker.utils.EmailUtils.getEmailMessage;
import static com.lps.ldtracker.utils.EmailUtils.getVerificationUrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lps.ldtracker.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Service 
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	@Value("${spring.mail.verify.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String fromEmail;
	private final TemplateEngine templateEngine;
	private final JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String subject, String body) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage(); 
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException msge) {
			msge.printStackTrace();
			log.debug(ERROR_MAIL_MSG + msge.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.debug(ERROR_MAIL + e.getMessage());
		}

	}
	
	@Override
	public void sendSimpleMailMessage(String name, String to, String token) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setText(getEmailMessage(name, host, token));
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}
		
	}
	
	@Override
	public void sendHtmlEmail(String name, String to, String username, 
			String password, String token) {
		try {
			Context context = new Context();
			context.setVariables(Map.of("name", name, 
					"url", getVerificationUrl(host, token), 
					"username", username, "password", password));
			String text = templateEngine.process(EMAIL_TEMPLATE, context); 
			MimeMessage message = javaMailSender.createMimeMessage();;
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(text, true);
			javaMailSender.send(message);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}		
	}

}
