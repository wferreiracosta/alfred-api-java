package br.com.wferreiracosta.alfred.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class SmtpEmailServiceImpl extends EmailServiceImpl {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        log.info("Enviando email...");
        mailSender.send(simpleMailMessage);
        log.info("Email enviado!");
    }

}
