package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;
import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.dtos.TransactionDTO;
import com.ohhigordev.transacao_simplificada.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.payer());
        User receiver = this.userService.findUserById(transaction.payee());

        // 1. Validar se a transação pode acontecer (Regras de negócio locais)
        userService.validateTransaction(sender, transaction.value());

        // 2. Consultar o serviço autorizador externo
        boolean isAuthorized = this.authService.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada pelo serviço externo.");
        }

        // 3. Lógica de "Transferir": tira de um, dá para o outro
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        // 4. Salvar no histórico de transações
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now()); // Note que corrigi para 'setTimestamp'

        // 5. Persistir alterações no banco
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        // 6. Enviar notificação (Opcional no fluxo principal, pode falhar sem dar rollback)
        this.notificationService.sendNotification(sender, "Transação enviada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }
}
