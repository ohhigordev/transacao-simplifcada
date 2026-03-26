package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;
import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.dtos.TransactionDTO;
import com.ohhigordev.transacao_simplificada.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private UserService userService;
    private TransactionRepository repository;

    @Transactional // Garante que se algo falhar, o dinheiro não suma
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.payer());
        User receiver = this.userService.findUserById(transaction.payee());

        // 1. Validar se a transação pode acontecer
        userService.validateTransaction(sender, transaction.value());

        // 2. Lógica de "Transferir": tira de um, dá para o outro
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        // 3. Salvar no histórico de transações
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now());

        // 4. Persistir alterações
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        return newTransaction;
    }

}
