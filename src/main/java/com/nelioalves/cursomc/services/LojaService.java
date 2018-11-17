package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.repositories.LojaRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class LojaService {

	@Autowired
	private LojaRepository lojaRepository;
	
	/**
	 * Recupera todas as lojas
	 * @return
	 */
	public List<Loja> findAll(){
		return lojaRepository.findAll();
	}
	
	public Loja findById(Integer id) {
		return lojaRepository.findById(id).orElseThrow(() ->
		new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Categoria.class.getName()));
	}
}
