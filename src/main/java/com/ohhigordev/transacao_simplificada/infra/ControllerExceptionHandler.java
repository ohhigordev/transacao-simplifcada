package com.ohhigordev.transacao_simplificada.infra;

import com.ohhigordev.transacao_simplificada.exceptions.InsufficientBalanceException;
import com.ohhigordev.transacao_simplificada.exceptions.UnauthorizedTransactionException;
import com.ohhigordev.transacao_simplificada.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException exception){
        return ResponseEntity.badRequest().body("Erro: Dados duplicados (Cpf ou e-mail já existem).");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleNotFound(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalance(InsufficientBalanceException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<String> handleUnauthorizedTransaction(UnauthorizedTransactionException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptionHandler(Exception exception){
        return ResponseEntity.internalServerError().body("Erro inesperado: " + exception.getMessage());
    }
}
