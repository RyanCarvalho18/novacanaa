package br.com.controlefinanceiro.novacanaa.controller;

import br.com.controlefinanceiro.novacanaa.dto.AccountRegistrationRequest;
import br.com.controlefinanceiro.novacanaa.dto.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.com.controlefinanceiro.novacanaa.service.AccountService;

@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String carregarPaginaListagem() {
        return "PaginaLogin";
    }

    @GetMapping("/logout-success")
    public String logout() {
        return "PaginaLogout";
    }

    @GetMapping("/")
    public String paginaPrincipal(){
        return "Home";
    }

    @GetMapping("/cadastro")
    public String cadastro(){return "Cadastro";}

    @PostMapping("/cadastro")
    public String ProcessarCadastro(AccountRegistrationRequest request){

    accountService.cadastrar(request);

     return "redirect:/login";}
}
