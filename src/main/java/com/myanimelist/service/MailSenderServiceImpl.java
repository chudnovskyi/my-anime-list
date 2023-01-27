package com.myanimelist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String mailname;

	@Override
	public void send(String emailTo, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(mailname);
		mailMessage.setTo(emailTo);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		javaMailSender.send(mailMessage);
	}
}
