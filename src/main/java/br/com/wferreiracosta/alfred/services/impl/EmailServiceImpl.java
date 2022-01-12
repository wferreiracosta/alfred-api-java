package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Pedido;
import br.com.wferreiracosta.alfred.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class EmailServiceImpl implements EmailService {

    @Value("${mail.default.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(pedido);
        this.sendEmail(simpleMailMessage);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(pedido.getCliente().getEmail());
        simpleMailMessage.setFrom(this.sender);
        simpleMailMessage.setSubject("Pedido Confirmado! Código: "+pedido.getId());
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText(pedido.toString());
        return simpleMailMessage;
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = this.prepareMimeFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm,true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido Confirmado! Código: "+obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(this.htmlFromTemplatePedido(obj),true);
        return mm;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

}
