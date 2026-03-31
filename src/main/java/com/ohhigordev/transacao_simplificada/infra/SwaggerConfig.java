package com.ohhigordev.transacao_simplificada.infra;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("PicPay Simplificado API")
                        .description("API para a transferência de dinheiro entre os usuários comuns e lojistas")
                        .version("v1.0"));
    }

}
