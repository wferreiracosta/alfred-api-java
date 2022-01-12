package br.com.wferreiracosta.alfred.config;

import br.com.wferreiracosta.alfred.services.DataBaseService;
import br.com.wferreiracosta.alfred.services.EmailService;
import br.com.wferreiracosta.alfred.services.impl.MockEmailServiceImpl;
import br.com.wferreiracosta.alfred.services.impl.SmtpEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DataBaseService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() {
        if(!"create".equals(strategy)){
            return false;
        }
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService smtpEmailService() {
        return new SmtpEmailServiceImpl();
    }

}
