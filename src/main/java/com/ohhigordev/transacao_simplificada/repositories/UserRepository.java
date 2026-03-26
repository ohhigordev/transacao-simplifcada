package com.ohhigordev.transacao_simplificada.repositories;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);

    Optional<Object> findUserById(Long id);
}
