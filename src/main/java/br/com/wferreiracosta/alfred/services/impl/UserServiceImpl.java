package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.config.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServiceImpl {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

}
