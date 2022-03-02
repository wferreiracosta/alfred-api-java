package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="instante") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction);

}
