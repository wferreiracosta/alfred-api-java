package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    public Optional<Categoria> findById(Integer id);

    public CategoriaDTO save(CategoriaDTO categoriaDTO);

    public CategoriaDTO update(CategoriaDTO categoriaDTO);

    public Optional<Categoria> delete(Integer id);

    public List<CategoriaDTO> findAll();

    public Page<CategoriaDTO> findAllWithPagination(Integer page, Integer linesPerPage, String orderBy, String direction);

}
