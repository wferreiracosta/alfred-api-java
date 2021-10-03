package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.controllers.utils.URL;
import br.com.wferreiracosta.alfred.exception.ObjectNotFoundException;
import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.models.dto.ProdutoDTO;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/{id}")
    public Produto findById(@PathVariable Integer id){
        log.info("[GET] Fazendo a busca do produto com o id: {}", id);
        return this.produtoService.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProdutoDTO> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return listDto;
    }
}
