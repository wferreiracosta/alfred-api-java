package br.com.wferreiracosta.alfred.models;

import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern="dd//MM/yyyy HH:mm")
    private LocalDateTime dataVencimento;

    @JsonFormat(pattern="dd//MM/yyyy HH:mm")
    private LocalDateTime dataPagamento;

    public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, LocalDateTime dataVencimento,
                              LocalDateTime dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

}
