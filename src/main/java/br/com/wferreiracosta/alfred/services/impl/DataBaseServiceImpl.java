package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.services.DataBaseService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    private final CategoriaRepository categoriaRepository;

    public DataBaseServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void instantiateTestDatabase() {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
    }

}
