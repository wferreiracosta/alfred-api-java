package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/pedidos")
public interface PedidoController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Pedido findById(@PathVariable Integer id);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj);

}
