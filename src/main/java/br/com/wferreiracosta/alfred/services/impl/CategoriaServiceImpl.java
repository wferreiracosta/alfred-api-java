package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.exception.DataIntegrityException;
import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = this.categoriaRepository.save(new Categoria(categoriaDTO));
        return new CategoriaDTO(categoria);
    }

    @Override
    public CategoriaDTO update(CategoriaDTO categoriaDTO) {
        Categoria categoriaOld = this.findById(categoriaDTO.getId()).get();
        categoriaOld.setNome(categoriaDTO.getNome());
        Categoria categoria = this.categoriaRepository.save(categoriaOld);
        return CategoriaDTO.builder().id(categoria.getId()).nome(categoria.getNome()).build();
    }

    @Override
    public Optional<Categoria> delete(Integer id) {
        Optional<Categoria> categoriaOptional = this.findById(id);
        try {
            categoriaOptional.ifPresent(this.categoriaRepository::delete);
            return categoriaOptional;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel apagar categoria que possui produtos");
        }
    }

    @Override
    public List<CategoriaDTO> findAll() {
        List<Categoria> categoriaList = this.categoriaRepository.findAll();
        return categoriaList
                .stream()
                .map(obj -> new CategoriaDTO(obj))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoriaDTO> findAllWithPagination(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.categoriaRepository.findAll(pageRequest)
                .map(obj -> new CategoriaDTO(obj));
    }

}
