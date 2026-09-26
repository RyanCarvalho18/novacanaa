package br.com.controlefinanceiro.novacanaa.controller;

import br.com.controlefinanceiro.novacanaa.movimentacao.Movimentacao;
import br.com.controlefinanceiro.novacanaa.service.MovimentacaoService;
import br.com.controlefinanceiro.novacanaa.service.ResumoFinanceiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService  movimentacaoService;

    @GetMapping
    public List<Movimentacao> listarMovimentacoes(){
        return movimentacaoService.listarMovimentacao();
    }

    @PostMapping
    @Transactional
    public Movimentacao salvarMovimentacoes(@RequestBody  Movimentacao movimentacao){
        return movimentacaoService.salvarMovimentacao(movimentacao);
    }

    @GetMapping("/{id}")
    @Transactional
    public Movimentacao buscarMovimentacoesPorId(@PathVariable Long id){
        return movimentacaoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public Movimentacao atualizarMovimentacoes(@PathVariable Long id, @RequestBody Movimentacao novosDados){
        return movimentacaoService.atualizarMovimentacao(id , novosDados);
    }

    @DeleteMapping("/{id}")
    public void deletarMovimentacoes(@PathVariable Long id){
        movimentacaoService.deletarMovimentacao(id);
    }

    @GetMapping("/resumo")
    public ResumoFinanceiro calcularResumoMovimentacoes(){
        return movimentacaoService.calcularResumo();
    }

}
