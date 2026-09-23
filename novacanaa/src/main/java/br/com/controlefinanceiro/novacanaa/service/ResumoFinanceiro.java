package br.com.controlefinanceiro.novacanaa.service;

import java.math.BigDecimal;

public record ResumoFinanceiro(BigDecimal totalEntrada, BigDecimal totalSaida, BigDecimal saldo ) {
}
