package br.com.wferreiracosta.alfred.controllers.impl;

import br.com.wferreiracosta.alfred.controllers.CategoriaController;
import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoriaControllerImpl implements CategoriaController {

    private final CategoriaService categoriaService;

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @Override
    public Categoria findById(@PathVariable Integer id) {
        log.info("[GET] Obtendo Categoria por id: ID = " + id);
        return this.categoriaService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    @Override
    public ResponseEntity<CategoriaDTO> save(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        log.info("[POST] Salvando Categoria: " + categoriaDTO);
        CategoriaDTO categoriaSalva = this.categoriaService.save(categoriaDTO);
        URI uri = getUri(categoriaSalva.getId());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @Override
    public CategoriaDTO update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
        log.info("[PUT] Alterando Categoria por id: ID = " + id);
        objDto.setId(id);
        return this.categoriaService.update(objDto);
    }

    @Override
    public Optional<Categoria> delete(@PathVariable Integer id) {
        log.info("[DELETE] Apagando Categoria por id: ID = " + id);
        return this.categoriaService.delete(id);
    }

    @Override
    public List<CategoriaDTO> findAll() {
        log.info("[GET] Obtendo todas as categorias");
        return this.categoriaService.findAll();
    }

    @Override
    public Page<CategoriaDTO> findAllWithPagination(Integer page, Integer linesPerPage,
                                                    String orderBy, String direction) {
        return this.categoriaService.findAllWithPagination(page, linesPerPage, orderBy, direction);
    }

}
