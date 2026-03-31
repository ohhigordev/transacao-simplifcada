package com.ohhigordev.transacao_simplificada.services;

import com.ohhigordev.transacao_simplificada.domain.user.User;
import com.ohhigordev.transacao_simplificada.dtos.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate;

    @Value("${api.url.notify}")
    private String url;

    public void sendNotification(User user, String message){
        NotificationDTO notificationRequest = new NotificationDTO(user.getEmail(), message);

        try{
            restTemplate.postForEntity(url, notificationRequest, String.class);
            log.info("Notificação enviada com sucesso para: {}", user.getEmail());
        }catch (Exception e){
            log.error("Erro ao enviar a notificação para {}: {}", user.getEmail(), e.getMessage());
        }
    }

}
