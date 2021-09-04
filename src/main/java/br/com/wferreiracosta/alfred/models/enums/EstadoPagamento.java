package br.com.wferreiracosta.alfred.models.enums;

import lombok.Getter;

@Getter
public enum EstadoPagamento {

    PENDENTE(1, "Pagamento Pendente"),
    QUITADO(2, "Pagamento Quitado"),
    CANCELADO(3, "Pagamento Cancelado");

    private int cod;
    private String descricao;

    private EstadoPagamento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static EstadoPagamento toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (EstadoPagamento x : EstadoPagamento.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("ID Inválido: " + cod);
    }

}
