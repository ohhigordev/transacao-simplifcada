package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;
import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.domain.user.UserType;
import com.ohhigordev.transacao_simplificada.dtos.TransactionDTO;
import com.ohhigordev.transacao_simplificada.repositories.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private TransactionService transactionService;


    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        // Arrange
        User sender = new User(1L, "Higor", "Dev", "123456", "h@t.com", "123", new BigDecimal(100), UserType.COMMON);
        User receiver = new User(2L, "João", "Lojista", "654321", "j@t.com", "123", new BigDecimal(50), UserType.MERCHANT);

        // Garantindo que o mock retorne EXATAMENTE essas instâncias acima
        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO dto = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        // Act
        transactionService.createTransaction(dto);

        // Assert
        // O valor esperado é 90 (100 - 10)
        assertEquals(new BigDecimal(90), sender.getBalance());
        assertEquals(new BigDecimal(60), receiver.getBalance());
    }



}
