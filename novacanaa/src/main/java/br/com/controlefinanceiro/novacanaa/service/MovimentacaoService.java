package br.com.controlefinanceiro.novacanaa.service;

import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import br.com.controlefinanceiro.novacanaa.movimentacao.MovimentacaoRepository;
import br.com.controlefinanceiro.novacanaa.movimentacao.TipoMovimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository repository;

    BigDecimal totalEntrada = BigDecimal.ZERO;
    BigDecimal totalSaida = BigDecimal.ZERO;
    BigDecimal saldo = BigDecimal.ZERO;

    public List<Movimentacao> listarMovimentacao(){
        return repository.findAll();
    }

    public Movimentacao salvarMovimentacao(Movimentacao movimentacao){
        return repository.save(movimentacao);
    }

    public Movimentacao buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public void deletarMovimentacao(Long id) {}

    public Movimentacao atualizarMovimentacao(Long id, Movimentacao novosDados){
        Movimentacao antiga = buscarPorId(id);

        antiga.setData(novosDados.getData());
        antiga.setDescricao(novosDados.getDescricao());
        antiga.setTipo(novosDados.getTipo());
        antiga.setValor(novosDados.getValor());

        return salvarMovimentacao(antiga);
    }

    public ResumoFinanceiro calcularResumo() {
        List<Movimentacao> mov = repository.findAll();
        for (Movimentacao m : mov) {
            if (m.getTipo() == TipoMovimentacao.ENTRADA) {
                totalEntrada = totalEntrada.add(m.getValor());
            } else {
                totalSaida = totalSaida.add(m.getValor());
            }
        }
        saldo = totalEntrada.subtract(totalSaida);
        return new ResumoFinanceiro(totalEntrada, totalSaida, saldo);
    }
}

