package com.ohhigordev.transacao_simplificada.repositories;

import com.ohhigordev.transacao_simplificada.domain.transaction.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
