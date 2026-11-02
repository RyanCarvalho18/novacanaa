package br.com.controlefinanceiro.novacanaa.controller;

import br.com.controlefinanceiro.novacanaa.molde.MovimentacaoFixa;
import br.com.controlefinanceiro.novacanaa.service.MovimentacaoFixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Mantendo o padrão do seu outro controller
    @RestController
    @RequestMapping("/movimentacoes-fixas")
    public class MovimentacaoFixaController {

        @Autowired
        private MovimentacaoFixaService movimentacaoFixaService;

        // Listar todos os gastos fixos (molde)
        @GetMapping
        public List<MovimentacaoFixa> listarMovimentacoesFixas() {
            return movimentacaoFixaService.listar();
        }

        // Cadastrar um novo gasto fixo (ex: Pão, Manteiga)
        @PostMapping
        public MovimentacaoFixa salvarMovimentacaoFixa(@RequestBody MovimentacaoFixa movimentacaoFixa) {
            return movimentacaoFixaService.salvar(movimentacaoFixa);
        }

        // Buscar um gasto fixo específico pelo ID
        @GetMapping("/{id}")
        public MovimentacaoFixa buscarPorId(@PathVariable Long id) {
            return movimentacaoFixaService.buscarPeloId(id);
        }

        // Atualizar os dados de um gasto fixo
        @PutMapping("/{id}")
        public MovimentacaoFixa atualizarMovimentacaoFixa(@PathVariable Long id, @RequestBody MovimentacaoFixa novosDados) {
            return movimentacaoFixaService.atualizarMovimentacao(id, novosDados);
        }

        // Deletar um gasto fixo
        @DeleteMapping("/{id}")
        public void deletarMovimentacaoFixa(@PathVariable Long id) {
            movimentacaoFixaService.deletar(id);
        }
}
