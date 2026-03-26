package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message){
        String url = " https://util.devi.tools/api/v1/notify";

        NotificationDTO notificarionRequest = new NotificationDTO(user.getEmail(), message);

        try{
            restTemplate.postForEntity(url, notificarionRequest, String.class);
        }catch (Exception e){
            System.out.println("Erro ao enviar a notificação: " + e.getMessage());
        }
    }

}
