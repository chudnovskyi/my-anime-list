package com.myanimelist.service.impl;

import com.myanimelist.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${host.domain}")
    private String hostDomain;

    @Override
    public void send(String username, String userEmail, String activationCode) {
        String message = """
                Hello, %s!
                Welcome to MyAnimeList. Please, follow link to verify your account:
                %s/register/activate/%s,
                """.formatted(username, hostDomain, activationCode);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Activation Code");
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
