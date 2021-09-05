package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Categoria findById(@PathVariable Integer id){
        log.info("[GET] Obtendo Categoria por id: ID = "+id);
        return this.categoriaService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria){
        log.info("[POST] Salvando Categoria: "+categoria);
        Categoria categoriaSalva = this.categoriaService.save(categoria);
        URI uri = getUri(categoriaSalva.getId());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
