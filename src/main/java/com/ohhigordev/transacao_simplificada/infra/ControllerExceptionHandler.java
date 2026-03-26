package com.ohhigordev.transacao_simplificada.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException exception){
        return ResponseEntity.badRequest().body("Erro: Dados multiplicados (Cpf ou e-mail já existem).");
    }

    // Caso não seja encontrado (ex: ID inexistente)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException exception){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralExceptionHandler(Exception exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }


}
