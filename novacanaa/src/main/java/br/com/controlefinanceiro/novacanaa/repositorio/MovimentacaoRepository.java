package br.com.controlefinanceiro.novacanaa.repositorio;

import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

}
