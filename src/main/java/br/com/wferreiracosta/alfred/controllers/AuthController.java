package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.dto.EmailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping(value = "/auth")
public interface AuthController {

    @PostMapping("/refresh_token")
    ResponseEntity<Void> refreshToken(@Valid @RequestBody HttpServletResponse response);

    @PostMapping("/forgot")
    ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO);

}
