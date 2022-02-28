package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.models.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/produtos")
public interface ProdutoController {

    @GetMapping(value = "/{id}")
    public Produto findById(@PathVariable Integer id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProdutoDTO> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    );

}
