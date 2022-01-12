package br.com.wferreiracosta.alfred.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

@Slf4j
public class MockEmailServiceImpl extends EmailServiceImpl {

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        log.info("Simulando envio de e-mail...");
        log.info(simpleMailMessage.toString());
        log.info("E-mail enviado...");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulando envio de email HTML...");
        log.info(msg.toString());
        log.info("Email enviado!");
    }

}
