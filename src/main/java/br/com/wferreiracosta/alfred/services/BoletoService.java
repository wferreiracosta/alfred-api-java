package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.PagamentoComBoleto;

import java.time.LocalDateTime;

public interface BoletoService {
    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, LocalDateTime instanteDoPedido);
}
