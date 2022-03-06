package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.services.AuthService;
import br.com.wferreiracosta.alfred.services.ClienteService;
import br.com.wferreiracosta.alfred.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClienteService clienteService;
    private final EmailService emailService;
    private final Random rand = new Random();
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void sendNewPassword(String email) {
        final var cliente = clienteService.findByEmail(email);
        if(isNull(cliente)){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        final var newPassoword = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPassoword));

        clienteService.insert(cliente);
        emailService.sendNewPasswordEmail(cliente, newPassoword);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i=0; i<10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (rand.nextInt(10) + 48);
        }
        else if (opt == 1) { // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }
        else { // gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
