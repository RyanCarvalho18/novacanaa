package br.com.controlefinanceiro.novacanaa.service;

import br.com.controlefinanceiro.novacanaa.molde.MovimentacaoFixa;
import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import br.com.controlefinanceiro.novacanaa.repositorio.MovimentacaoFixaRepository;
import br.com.controlefinanceiro.novacanaa.repositorio.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimentacaoFixaService {
    @Autowired
    private MovimentacaoFixaRepository fixaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    //Metodos de gestao do servico

    public MovimentacaoFixa salvar(MovimentacaoFixa movimentacaoFixa) {
        return fixaRepository.save(movimentacaoFixa);

    }

    public MovimentacaoFixa salvarNovaMovimentacaoFixa (MovimentacaoFixa movimentacaoFixa) {
        return fixaRepository.save(movimentacaoFixa);
    }

    public MovimentacaoFixa buscarPeloId(Long id) {
        return fixaRepository.findById(id).orElse(null);
    }
    public List<MovimentacaoFixa> listar() {
        return fixaRepository.findAll();
    }

    public void deletar(Long id) {
        fixaRepository.deleteById(id);
    }

    public MovimentacaoFixa atualizarMovimentacao(Long id, MovimentacaoFixa novosDados){
        MovimentacaoFixa anterior = buscarPeloId(id);

        anterior.setDescricao(novosDados.getDescricao());
        anterior.setTipo(novosDados.getTipo());
        anterior.setValor(novosDados.getValor());

        return salvarNovaMovimentacaoFixa(anterior);
    }

    @Scheduled(cron = "0 0 0 * * *") // Roda todo dia à meia-noite
    @Transactional
    public void processarLancamentosAutomaticos() {
        int diaHoje = LocalDate.now().getDayOfMonth();

        // Busca apenas os moldes que devem ser lançados hoje e estão ativos
        List<MovimentacaoFixa> moldesDeHoje = fixaRepository.findByDiaDoMesAndAtivoTrue(diaHoje);

        for (MovimentacaoFixa molde : moldesDeHoje) {
            // Transforma o MOLDE em uma MOVIMENTAÇÃO REAL
            Movimentacao real = new Movimentacao();
            real.setData(LocalDate.now());
            real.setDescricao(molde.getDescricao());
            real.setValor(molde.getValor());
            real.setTipo(molde.getTipo());
            real.setAccount(molde.getAccount()); // Importante para saber de quem é o gasto

            movimentacaoRepository.save(real);
        }
    }
}
