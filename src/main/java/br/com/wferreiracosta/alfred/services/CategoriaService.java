package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Categoria;

import java.util.Optional;

public interface CategoriaService {

    public Optional<Categoria> findById(Integer id);

}
