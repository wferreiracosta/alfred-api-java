package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.services.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        log.info("[PUT] Atualizando Cliente por id: ID = "+id);
        objDto.setId(id);
        clienteService.update(objDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        log.info("[DELETE] Apagando Cliente por id: ID = "+id);
        this.clienteService.delete(id);
    }

}
