package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Categoria save(@RequestBody Categoria categoria){
        log.info("[POST] Salvando Categoria: "+categoria);
        return this.categoriaService.save(categoria);
    }

}
