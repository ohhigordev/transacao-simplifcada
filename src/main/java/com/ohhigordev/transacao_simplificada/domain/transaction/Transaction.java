package com.ohhigordev.transacao_simplificada.domain.transaction;


import com.ohhigordev.transacao_simplificada.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;

    @ManyToOne // Um usuário pode fazer varias transações como pagador
    @JoinColumn(name = "sender_id")
    private User sender;


    @ManyToOne // Um usuário pode fazer varias transações como recebedor
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime timesTamp;
}
