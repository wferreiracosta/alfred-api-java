package br.com.wferreiracosta.alfred.controllers.impl;

import br.com.wferreiracosta.alfred.controllers.ProdutoController;
import br.com.wferreiracosta.alfred.controllers.utils.URL;
import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.models.dto.ProdutoDTO;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProdutoControllerImpl implements ProdutoController {

    private final ProdutoService produtoService;

    @Override
    public Produto findById(@PathVariable Integer id) {
        log.info("[GET] Fazendo a busca do produto com o id: {}", id);
        return this.produtoService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
    }

    @Override
    public Page<ProdutoDTO> findPage(String nome, String categorias,
                                     Integer page, Integer linesPerPage, String orderBy, String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return listDto;
    }

}
