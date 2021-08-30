package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/produtos")
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/{id}")
    public Optional<Produto> findById(@PathVariable Integer id){
        log.info("[GET] Fazendo a busca do produto com o id: {}", id);
        return this.produtoService.findById(id);
    }
}
