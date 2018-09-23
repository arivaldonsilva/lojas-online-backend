package com.nelioalves.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email nao encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	// gera uma string de 10 caracteres aleatorios, será uma nova senha temporaria
	private String newPassword() {
		char vet[] = new char[10];
		for(int i=0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// gera caracteres aleatorios podendo ser digitos decimais, letras maiusculas ou letras minusculas
	private char randomChar() {
		int opt =rand.nextInt(3);
		if(opt==0) {// gera um dígito
			return (char) (rand.nextInt(10) + 48);
		}else if(opt==1) {// gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}else {//gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
