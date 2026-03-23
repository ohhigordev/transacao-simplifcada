package com.ohhigordev.transacao_simplificada.repositories;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
