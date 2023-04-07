package com.myanimelist.unit.service;

import com.myanimelist.service.impl.MailSenderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailSenderServiceImpl mailSenderService;

    @Test
    void testSend() {
        String username = "dummy_username";
        String userEmail = "dummy_email";
        String activationCode = "dummy_code";

        mailSenderService.send(username, userEmail, activationCode);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
