# 📧 API Notificação (Core SMTP & Templates)

## 📌 Visão Geral
Microsserviço isolado dedicado exclusivamente ao disparo automatizado de e-mails transacionais. Utiliza o ecossistema de templates dinâmicos para formatação de mensagens corporativas.

## 🛠️ Stack Tecnológico
* **Java 21** | **Spring Boot 3.4.x**
* **Spring Boot Starter Mail (SMTP / TLS)**
* **Thymeleaf (Engine de Templates)**
* **Springdoc OpenAPI (Swagger UI)**

## 🚀 Como Executar Localmente
1. Configure as credenciais de SMTP (ex: Gmail App Password) no seu `application-dev.yml`.
2. Execute a aplicação:
   ```bash
   ./gradlew bootRun
   ```

## 🔌 Documentação (Swagger)
Com a aplicação rodando na porta `8083`, acesse a documentação interativa:
🔗 [Swagger UI - API Notificação](http://localhost:8083/swagger-ui/index.html)