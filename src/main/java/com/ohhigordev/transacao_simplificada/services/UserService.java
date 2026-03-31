package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.domain.user.UserType;
import com.ohhigordev.transacao_simplificada.dtos.UserDTO;
import com.ohhigordev.transacao_simplificada.exceptions.InsufficientBalanceException;
import com.ohhigordev.transacao_simplificada.exceptions.UnauthorizedTransactionException;
import com.ohhigordev.transacao_simplificada.exceptions.UserNotFoundException;
import com.ohhigordev.transacao_simplificada.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new UnauthorizedTransactionException("Lojistas não podem realizar transferências");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    public void saveUser(User user){
        repository.save(user);
    }

    public User createUser(UserDTO data){
        User newUser = new User();
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setBalance(data.balance());
        newUser.setUserType(data.userType());
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setEmail(data.email());
        newUser.setDocument(data.document());

        repository.save(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }
}
