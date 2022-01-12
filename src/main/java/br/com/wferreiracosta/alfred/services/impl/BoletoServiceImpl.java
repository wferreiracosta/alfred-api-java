package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.PagamentoComBoleto;
import br.com.wferreiracosta.alfred.services.BoletoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoServiceImpl implements BoletoService {
    @Override
    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, LocalDateTime instanteDoPedido) {
        instanteDoPedido.plusDays(7);
        pagto.setDataVencimento(instanteDoPedido);
    }
}
