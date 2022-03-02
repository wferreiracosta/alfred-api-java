package br.com.wferreiracosta.alfred.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/auth")
public interface AuthController {

    @PostMapping("/refresh_token")
    ResponseEntity<Void> refreshToken(HttpServletResponse response);

}
