package com.rafael.notificacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"jwt.secret=chave-falsa-para-testes-no-ci-nao-usar-em-producao",
		"jwt.expiration=86400000",
		"spring.mail.username=teste@teste.com",
		"spring.mail.password=teste123"
})
class NotificacaoApplicationTests {

	@Test
	void contextLoads() {
	}
}