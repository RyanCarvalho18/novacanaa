package br.com.controlefinanceiro.novacanaa.movimentacao;


import br.com.controlefinanceiro.novacanaa.usuario.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name="movimentacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String descricao;


    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    //fazendo o vinculo com o account
    @ManyToOne
    @JoinColumn(name = "account_id") //definindo o nome da coluna no banco de dados
    private Account account;
}
