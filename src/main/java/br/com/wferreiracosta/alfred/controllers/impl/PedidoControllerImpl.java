package br.com.wferreiracosta.alfred.controllers.impl;

import br.com.wferreiracosta.alfred.controllers.PedidoController;
import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Pedido;
import br.com.wferreiracosta.alfred.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController

@RequiredArgsConstructor
public class PedidoControllerImpl implements PedidoController {

    private final PedidoService pedidoService;

    @Override
    public Pedido findById(@PathVariable Integer id) {
        return this.pedidoService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + id));
    }

    @Override
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
        obj = this.pedidoService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
