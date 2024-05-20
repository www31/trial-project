package io.getarrays.userservice.service.impl;

import static io.getarrays.userservice.utils.EmailUtils.getEmailMessage;
import static io.getarrays.userservice.utils.EmailUtils.getVerificationUrl;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import io.getarrays.userservice.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

/**
 * @author Junior RT
 * @version 1.0
 * @license Get Arrays, LLC (https://getarrays.io)
 * @since 6/25/2023
 */
@Service
@RequiredArgsConstructor
@Async
public class EmailServiceImpl implements EmailService {
	private static final String TEXT_HTML_ENCODING = "text/html";
	private static final String EMAIL_TEMPLATE = "emailTemplate";
	private static final String UTF_8_ENCODING = "UTF-8";
	private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
	@Value("${spring.mail.verify.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String fromEmail;
	private final JavaMailSender emailSender;
	private final TemplateEngine templateEngine;
	
	@Override
	public void sendSimpleMailMessage(String name, String to, String token) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setText(getEmailMessage(name, host, token));
			emailSender.send(message);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}
	}

	@Override
	public void sendMimeMessageWithAttachments(String name, String to, String token) {
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(getEmailMessage(name, host, token));
			//Add attachments
			FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.jpg"));
			FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
			FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
			helper.addAttachment(fort.getFilename(), fort);
			helper.addAttachment(dog.getFilename(), dog);
			helper.addAttachment(homework.getFilename(), homework);
			emailSender.send(message);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}
		
	}
	

	@Override
	public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(getEmailMessage(name, host, token));
			//Add attachments
			FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.jpg"));
			FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
			FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
			helper.addInline(getContentId(fort.getFilename()), fort);
			helper.addInline(getContentId(dog.getFilename()), dog);
			helper.addInline(getContentId(homework.getFilename()), homework);
			emailSender.send(message);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}
		
	}

	@Override
	public void sendHtmlEmail(String name, String to, String token) {
		try {
			Context context = new Context();
			context.setVariables(Map.of("name", name, "url", getVerificationUrl(host, token)));
			String text = templateEngine.process(EMAIL_TEMPLATE, context); 
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setText(text, true);
			emailSender.send(message);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}		
	}

	@Override
	public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {
		try {
			MimeMessage message = getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
			helper.setPriority(1);
			helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
			helper.setFrom(fromEmail);
			helper.setTo(to);
			//helper.setText(text, true);
			Context context = new Context();
			context.setVariables(Map.of("name", name, "url", getVerificationUrl(host, token)));
			String text = templateEngine.process(EMAIL_TEMPLATE, context);
			//Add attachments
			//Add HTML email body
			MimeMultipart mimeMultipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(text, TEXT_HTML_ENCODING);
			mimeMultipart.addBodyPart(messageBodyPart);
			//Add images to the email body
			BodyPart imageBodyPart = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/dog.jpg");
			imageBodyPart.setDataHandler(new DataHandler(dataSource));
			imageBodyPart.setHeader("Content-ID", "image");
			mimeMultipart.addBodyPart(imageBodyPart);
			message.setContent(mimeMultipart);
			emailSender.send(message);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new RuntimeException(exception.getMessage());
		}	
		
	}

	private MimeMessage getMimeMessage() {
		return emailSender.createMimeMessage();
	}

	private String getContentId(String filename) {
		return "<" + filename + ">";
	}

}
