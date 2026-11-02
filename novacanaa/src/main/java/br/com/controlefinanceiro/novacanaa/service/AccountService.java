package br.com.controlefinanceiro.novacanaa.service;

import br.com.controlefinanceiro.novacanaa.dto.AccountRegistrationRequest;
import br.com.controlefinanceiro.novacanaa.dto.AccountResponse;
import br.com.controlefinanceiro.novacanaa.repositorio.AccountRepository;
import br.com.controlefinanceiro.novacanaa.usuario.Account;
import br.com.controlefinanceiro.novacanaa.usuario.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AccountResponse cadastrar(AccountRegistrationRequest request) {

    if (accountRepository.existsByUsername(request.username())) {
      throw new RuntimeException("este usuário já está em uso!");
    }

    if (accountRepository.existsByEmail(request.email())) {
      throw new RuntimeException("este email já está em uso!");
    }

    // 2. Cria a entidade Account
    Account account = new Account();
    account.setUsername(request.username());
    account.setEmail(request.email());

    // IMPORTANTE: Criptografa a senha antes de salvar!
    String senhaCriptografada = passwordEncoder.encode(request.password());
    account.setPassword(senhaCriptografada);

    // Define a Role padrão como USER (ou a que você preferir)
    account.setRole(AccountRole.USER);

    // 3. Salva no banco de dados
    Account accountSalva = accountRepository.save(account);

    // 4. Retorna o record UserResponse (com os dados da conta salva)
    return new AccountResponse(
            accountSalva.getId(),
            accountSalva.getUsername(),
            accountSalva.getEmail()
    );

  }
}

