package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/categorias")
public interface CategoriaController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Categoria findById(@PathVariable Integer id);

    @PostMapping
    public ResponseEntity<CategoriaDTO> save(@Valid @RequestBody CategoriaDTO categoriaDTO);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}")
    public CategoriaDTO update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Optional<Categoria> delete(@PathVariable Integer id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoriaDTO> findAll();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/page")
    public Page<CategoriaDTO> findAllWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    );

}
