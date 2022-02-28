package br.com.wferreiracosta.alfred.controllers.impl;

import br.com.wferreiracosta.alfred.controllers.ClienteController;
import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.services.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClienteControllerImpl implements ClienteController {

    private final ClienteService clienteService;

    @Override
    public Cliente findById(@PathVariable Integer id) {
        log.info("[GET] Obtendo Cliente por id: ID = " + id);
        return this.clienteService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id));
    }

    @Override
    public void update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        log.info("[PUT] Atualizando Cliente por id: ID = " + id);
        objDto.setId(id);
        clienteService.update(objDto);
    }

    @Override
    public void delete(@PathVariable Integer id) {
        log.info("[DELETE] Apagando Cliente por id: ID = " + id);
        this.clienteService.delete(id);
    }

    @Override
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.insert(clienteNewDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
