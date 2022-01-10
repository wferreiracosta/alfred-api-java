package br.com.wferreiracosta.alfred.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

@Slf4j
public class MockEmailServiceImpl extends EmailServiceImpl {

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        log.info("Simulando envio de e-mail...");
        log.info(simpleMailMessage.toString());
        log.info("E-mail enviado...");
    }

}
