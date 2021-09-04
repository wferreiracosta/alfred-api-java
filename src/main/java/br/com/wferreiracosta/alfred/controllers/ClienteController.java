package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.services.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Integer id){
        log.info("[GET] Obtendo Cliente por id: ID = "+id);
        return this.clienteService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id));
    }

}
