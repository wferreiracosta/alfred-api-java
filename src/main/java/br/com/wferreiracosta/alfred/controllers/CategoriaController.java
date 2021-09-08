package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Categoria findById(@PathVariable Integer id){
        log.info("[GET] Obtendo Categoria por id: ID = "+id);
        return this.categoriaService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> save(@Valid @RequestBody CategoriaDTO categoriaDTO){
        log.info("[POST] Salvando Categoria: "+categoriaDTO);
        CategoriaDTO categoriaSalva = this.categoriaService.save(categoriaDTO);
        URI uri = getUri(categoriaSalva.getId());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}")
    public CategoriaDTO update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
        log.info("[PUT] Alterando Categoria por id: ID = "+id);
        objDto.setId(id);
        return this.categoriaService.update(objDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Optional<Categoria> delete(@PathVariable Integer id) {
        log.info("[DELETE] Apagando Categoria por id: ID = "+id);
        return this.categoriaService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoriaDTO> findAll(){
        log.info("[GET] Obtendo todas as categorias");
        return this.categoriaService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/page")
    public Page<CategoriaDTO> findAllWithPagination(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value="direction", defaultValue = "ASC") String direction
    ){
        return this.categoriaService.findAllWithPagination(page, linesPerPage, orderBy, direction);
    }

}
