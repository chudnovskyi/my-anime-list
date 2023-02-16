package com.myanimelist.service;

public interface MailSenderService {

	void send(String emailTo, String subject, String message);
}
