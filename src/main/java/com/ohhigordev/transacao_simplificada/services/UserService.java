package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.domain.user.UserType;
import com.ohhigordev.transacao_simplificada.dtos.UserDTO;
import com.ohhigordev.transacao_simplificada.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        repository.save(user);
    }

    public User createUser(UserDTO data){
        User newUser = new User();
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setBalance(data.balance());
        newUser.setUserType(data.userType());
        newUser.setPassword(data.password());
        newUser.setEmail(data.email());
        newUser.setDocument(data.document());

        // Criptografando a senha recebida no DTO antes de colocar na entidade
        newUser.setPassword(passwordEncoder.encode(data.password()));


        repository.save(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }
}
