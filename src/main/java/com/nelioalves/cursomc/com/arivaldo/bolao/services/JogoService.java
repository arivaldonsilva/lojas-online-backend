package com.nelioalves.cursomc.com.arivaldo.bolao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.com.arivaldo.bolao.domain.Jogo;
import com.nelioalves.cursomc.com.arivaldo.bolao.repositories.JogoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class JogoService {

	@Autowired
	private JogoRepository repo;
	
	public Jogo buscar(Integer id) {
		return repo.findById(id).orElseThrow(() ->
				new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Jogo.class.getName()));
	}
	
	public List<Jogo> buscarTodos(){
		return repo.findAll();
	}
}
