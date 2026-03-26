package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.domain.user.UserType;
import com.ohhigordev.transacao_simplificada.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {


    private UserRepository repository;


    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Lojistas não podem realizar transferências");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado com o ID: " + id));
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
