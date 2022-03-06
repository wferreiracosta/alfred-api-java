package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);
    void sendEmail(SimpleMailMessage simpleMailMessage);
    void sendOrderConfirmationHtmlEmail(Pedido obj);
    void sendHtmlEmail(MimeMessage mimeMessage);
    void sendNewPasswordEmail(Cliente cliente, String newPassword);

}
