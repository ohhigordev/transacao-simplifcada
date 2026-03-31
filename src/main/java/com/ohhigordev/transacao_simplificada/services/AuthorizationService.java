package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final RestTemplate restTemplate;

    @Value("${api.url.authorize}")
    private String url;

    public boolean authorizeTransaction(User sender, BigDecimal value){
        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(url, Map.class);

            if (authorizationResponse.getStatusCode() == HttpStatus.OK){
                String message = (String) authorizationResponse.getBody().get("message");
                return "Autorizado".equalsIgnoreCase(message);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
