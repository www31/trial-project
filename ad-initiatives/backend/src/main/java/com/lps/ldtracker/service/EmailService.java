package com.lps.ldtracker.service;

public interface EmailService {

	public void sendEmail(String to, String subject, String body);

	public void sendHtmlEmail(String name, String to, String username, String password, String token);

	public void sendSimpleMailMessage(String name, String to, String token);
}
