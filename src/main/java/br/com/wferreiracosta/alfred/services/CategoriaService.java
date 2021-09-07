package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;

import java.util.Optional;

public interface CategoriaService {

    public Optional<Categoria> findById(Integer id);

    public Categoria save(Categoria categoria);

    public CategoriaDTO update(CategoriaDTO categoriaDTO);

    public void delete(Integer id);

}
