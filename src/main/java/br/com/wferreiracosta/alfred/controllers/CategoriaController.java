package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping(value = "/{id}")
    public Optional<Categoria> findById(@PathVariable Integer id){
        log.info("[GET] Obtendo Categoria por id: ID = "+id);
        return this.categoriaService.findById(id);
    }

}
