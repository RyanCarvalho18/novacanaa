package br.com.controlefinanceiro.novacanaa.molde;

import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import br.com.controlefinanceiro.novacanaa.movimentacao.TipoMovimentacao;
import br.com.controlefinanceiro.novacanaa.usuario.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;

@Entity
@Table(name = "movimentacao_fixa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoFixa {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            private String descricao;

             private BigDecimal valor;

             @Enumerated(EnumType.STRING)
             private TipoMovimentacao tipo;

             //usado para saber qual dia do mes fazer o lancamento dessa movimentacao fixa
             private Integer diaDoMes;


             //usado para saber de quem é esse gasto
             @ManyToOne
             @JoinColumn(name= "account_id")
             private Account account;

             //desativar sem precisar apagar o historico
             private boolean ativo = true;
}
