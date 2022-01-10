package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);
    void sendEmail(SimpleMailMessage simpleMailMessage);

}
