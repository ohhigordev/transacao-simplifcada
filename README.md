# Transação Simplificada - API de Pagamentos

Esta é uma solução para o desafio de back-end de transações simplificadas, desenvolvida com foco em arquitetura limpa, escalabilidade e boas práticas de desenvolvimento (SOLID, DRY). O sistema permite a transferência de dinheiro entre usuários comuns e lojistas, com validações de saldo, tipo de usuário e integração com serviços externos.

---

## 🚀 Tecnologias e Ferramentas

O projeto utiliza o que há de mais moderno no ecossistema Java para garantir performance e segurança:

*   **Java 21**: Uso das últimas funcionalidades da linguagem, como Records e Virtual Threads.
*   **Spring Boot 3+**: Framework base para construção da API REST.
    *   **Spring Data JPA**: Abstração da camada de persistência com Hibernate.
    *   **Spring Security**: Camada de proteção e configuração de segurança.
    *   **Spring Validation (Bean Validation)**: Garante a integridade dos dados de entrada.
*   **PostgreSQL**: Banco de dados relacional robusto para armazenamento de dados.
*   **Docker & Docker Compose**: Containerização para facilitar o setup do ambiente de desenvolvimento.
*   **Lombok**: Redução de código boilerplate (Getters, Setters, Construtores).
*   **SpringDoc (Swagger/OpenAPI 3)**: Documentação interativa da API para facilitar o consumo por outros desenvolvedores.
*   **JUnit 5 & Mockito**: Garantia da qualidade de software através de testes automatizados.

---

## 🏗️ Arquitetura do Projeto

A aplicação segue uma estrutura em camadas bem definida para facilitar a manutenção e evolução:

1.  **Domain**: Representa as entidades de negócio (`User`, `Transaction`) e regras centrais.
2.  **DTOs (Data Transfer Objects)**: Camada de transporte de dados entre o cliente e a API, isolando o domínio.
3.  **Controllers**: Exposição dos endpoints REST e tratamento das requisições HTTP.
4.  **Services**: Onde reside a lógica de negócio (validações, cálculos, integrações).
5.  **Repositories**: Interface de comunicação com o banco de dados.
6.  **Infra/Exception Handling**: Configurações globais e tratamento centralizado de erros para respostas amigáveis.

---

## 🛠️ Funcionalidades Principais

*   **Cadastro de Usuários**: Suporte a dois tipos de contas:
    *   **Usuários Comuns**: Podem enviar e receber dinheiro.
    *   **Lojistas (Merchants)**: Apenas recebem transferências.
*   **Transações Financeiras**:
    *   Validação de saldo antes de cada envio.
    *   Consulta a um serviço autorizador externo antes de finalizar a transação.
    *   Operação transacional (garante que se algo falhar, o saldo não seja descontado incorretamente).
*   **Notificações**: Após cada transação bem-sucedida, o sistema envia notificações aos envolvidos via serviço externo.
*   **Tratamento de Erros**: Respostas HTTP semânticas e detalhadas para cenários como saldo insuficiente, usuário não encontrado ou transação não autorizada.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
*   Java 21+
*   Maven
*   Docker & Docker Compose

### Passo a Passo

1.  **Clonar o repositório**:
    ```bash
    git clone https://github.com/seu-usuario/transacao-simplificada.git
    cd transacao-simplificada
    ```

2.  **Subir o Banco de Dados (Docker)**:
    ```bash
    docker-compose up -d
    ```

3.  **Compilar e Rodar a Aplicação**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## 📖 Documentação da API (Swagger)

Uma vez que a aplicação esteja rodando, você pode acessar a documentação interativa e testar os endpoints através do Swagger:

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ✅ Testes

Para garantir a confiabilidade das regras de negócio, o projeto conta com suíte de testes unitários. Para executá-los:

```bash
mvn test
```

---

## 🌟 Pontos Fortes e Diferenciais

*   **Código Limpo**: Estrutura organizada que facilita a leitura e o onboarding de novos desenvolvedores.
*   **Tratamento de Exceções**: Uso de `ControllerAdvice` para capturar erros e retornar mensagens claras ao usuário.
*   **Segurança e Escalabilidade**: Configurações preparadas para ambientes produtivos com PostgreSQL e Java 21.
*   **Interoperabilidade**: API pronta para ser consumida por Front-ends ou outros microserviços, com documentação OpenAPI completa.
