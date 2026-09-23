package br.com.controlefinanceiro.novacanaa.movimentacao;


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

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private BigDecimal valor;
}
