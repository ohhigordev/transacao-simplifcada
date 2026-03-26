package com.ohhigordev.transacao_simplificada.dtos;

import com.ohhigordev.transacao_simplificada.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String firstName,
        String lastName,
        String document,
        BigDecimal balance,
        String email,
        String password,
        UserType userType
) {}
