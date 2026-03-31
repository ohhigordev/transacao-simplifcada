package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;
import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.dtos.TransactionDTO;
import com.ohhigordev.transacao_simplificada.exceptions.UnauthorizedTransactionException;
import com.ohhigordev.transacao_simplificada.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;
    private final AuthorizationService authService;
    private final NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) {
        User sender = this.userService.findUserById(transaction.payer());
        User receiver = this.userService.findUserById(transaction.payee());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authService.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new UnauthorizedTransactionException("Transação não autorizada pelo serviço externo.");
        }

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now());

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação enviada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }
}
