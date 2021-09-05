package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Pedido;
import br.com.wferreiracosta.alfred.services.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Pedido findById(@PathVariable Integer id){
        log.info("[GET] Obtendo Pedido por id: ID = "+id);
        return this.pedidoService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + id));
    }

}
