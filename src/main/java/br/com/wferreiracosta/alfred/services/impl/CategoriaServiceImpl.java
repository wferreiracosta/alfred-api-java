package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        return this.categoriaRepository.findById(id);
    }

}
