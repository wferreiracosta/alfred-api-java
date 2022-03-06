package br.com.wferreiracosta.alfred.controllers.impl;

import br.com.wferreiracosta.alfred.controllers.AuthController;
import br.com.wferreiracosta.alfred.models.dto.EmailDTO;
import br.com.wferreiracosta.alfred.services.AuthService;
import br.com.wferreiracosta.alfred.services.impl.UserServiceImpl;
import br.com.wferreiracosta.alfred.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Override
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        final var user = UserServiceImpl.authenticated();
        final var token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer "+token);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> forgot(EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

}
