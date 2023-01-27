package com.myanimelist.service;

public interface MailSenderService {

	public void send(String emailTo, String subject, String message);
}
