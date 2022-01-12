package br.com.wferreiracosta.alfred.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@Slf4j
public class SmtpEmailServiceImpl extends EmailServiceImpl {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        log.info("Enviando email...");
        mailSender.send(simpleMailMessage);
        log.info("Email enviado!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Enviando email HTML...");
        javaMailSender.send(msg);
        log.info("Email enviado!");
    }

}
