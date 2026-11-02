package br.com.controlefinanceiro.novacanaa.repositorio;

import br.com.controlefinanceiro.novacanaa.molde.MovimentacaoFixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoFixaRepository extends JpaRepository<MovimentacaoFixa, Long> {
    List<MovimentacaoFixa> findByDiaDoMesAndAtivoTrue(Integer diaDoMes);
}
