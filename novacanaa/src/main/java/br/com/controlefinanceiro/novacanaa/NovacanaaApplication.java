package br.com.controlefinanceiro.novacanaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NovacanaaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovacanaaApplication.class, args);
	}

}
