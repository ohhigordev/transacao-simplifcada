package com.ohhigordev.transacao_simplificada.dtos;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal value,
        Long payer,
        Long payee)
{}
