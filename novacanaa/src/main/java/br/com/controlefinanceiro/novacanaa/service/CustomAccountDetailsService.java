package br.com.controlefinanceiro.novacanaa.service;

import br.com.controlefinanceiro.novacanaa.repositorio.AccountRepository;
import br.com.controlefinanceiro.novacanaa.usuario.Account; // Sua entidade do banco
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos a SUA entidade User do banco de dados
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 2. Agora criamos o objeto que o Spring Security espera (UserDetails)
        // Usamos o nome completo da classe do Spring para não conflitar com a sua classe User
        return org.springframework.security.core.userdetails.User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().name().replace("ROLE_", ""))
                .build();
    }
}