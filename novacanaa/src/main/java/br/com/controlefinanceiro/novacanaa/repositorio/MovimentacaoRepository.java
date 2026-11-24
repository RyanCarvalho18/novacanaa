package br.com.controlefinanceiro.novacanaa.repositorio;

import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import br.com.controlefinanceiro.novacanaa.movimentacao.TipoMovimentacao;
import br.com.controlefinanceiro.novacanaa.usuario.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT SUM(m.valor) FROM Movimentacao m WHERE m.account = :account AND m.tipo = :tipo")
    BigDecimal somarValorPorTipo(@Param("account")Account account, @Param("tipo") TipoMovimentacao tipo);
}
