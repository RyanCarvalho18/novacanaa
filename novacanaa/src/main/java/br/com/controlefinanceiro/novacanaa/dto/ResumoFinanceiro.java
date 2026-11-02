package br.com.controlefinanceiro.novacanaa.dto;

import java.math.BigDecimal;

public record ResumoFinanceiro(BigDecimal totalEntrada, BigDecimal totalSaida, BigDecimal saldo ) {
}
