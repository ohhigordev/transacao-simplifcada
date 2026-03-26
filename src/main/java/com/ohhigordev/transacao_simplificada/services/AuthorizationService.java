package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean authorizeTransaction(User sender, BigDecimal value){

        String url = "https://util.devi.tools/api/v2/authorize";

        // Simples chamada GET
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else{
            return false;
        }
    }

}
