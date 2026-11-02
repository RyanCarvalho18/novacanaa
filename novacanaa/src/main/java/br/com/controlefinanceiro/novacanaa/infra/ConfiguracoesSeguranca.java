package br.com.controlefinanceiro.novacanaa.infra;

import br.com.controlefinanceiro.novacanaa.service.CustomAccountDetailsService; // Importe o seu service!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {

    @Autowired
    private CustomAccountDetailsService customUserDetailsService; // Injetando o seu serviço de usuários

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        return http

                .authorizeHttpRequests( req -> {
                    // Adicionei "/register" aqui para você conseguir criar contas
                    req.requestMatchers("/login","/cadastro", "/register", "/css/**", "/js/**", "/static/**", "/style.css", "/script.js", "/assets/**").permitAll();
                    req.anyRequest().authenticated();
                } )
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .userDetailsService(customUserDetailsService) // ESSA É A LINHA QUE RESOLVE O ERRO!
                        .key("lembrarDeMim")
                )
                .build();
    }
}
