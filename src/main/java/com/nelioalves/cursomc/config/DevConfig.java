package com.nelioalves.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nelioalves.cursomc.services.DBService;
import com.nelioalves.cursomc.services.EmailService;
import com.nelioalves.cursomc.services.MockEmailService;
import com.nelioalves.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev") // Essa configuracao será utilizada somente quando o profile for dev em application.properties
public class DevConfig {
	
	@Autowired
	DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}") // busca qual a condicao de criacao do banco
	private String strategy;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		if(!strategy.equals("create")) {
			return false;
		}
		// Somente carrega os dados se strategy for "create"
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
