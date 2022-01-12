package br.com.wferreiracosta.alfred.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@ToString
@NoArgsConstructor
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime instante;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(Integer id, LocalDateTime instante, Cliente cliente, Endereco enderecoEntrega) {
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }

    public double getValorTotal(){
        double soma = 0.0;
        for(ItemPedido itemPedido : this.getItens()){
            soma += itemPedido.getSubTotal();
        }
        return soma;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("Pedido  Numero: ");
        builder.append(this.getId());
        builder.append(", Instante: ");
        builder.append(this.getInstante().format(formatter));
        builder.append(", Cliente: ");
        builder.append(this.getCliente().getNome());
        builder.append(", Situação do pagamento: ");
        builder.append(this.getPagamento().getEstado().getDescricao());
        builder.append("\nDetalhes:\n");
        this.getItens().forEach(x -> builder.append(x.toString()));
        builder.append("Valor Total: ");
        builder.append(nf.format(this.getValorTotal()));
        return builder.toString();
    }

}
