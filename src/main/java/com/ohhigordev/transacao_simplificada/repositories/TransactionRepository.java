package com.ohhigordev.transacao_simplificada.repositories;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
