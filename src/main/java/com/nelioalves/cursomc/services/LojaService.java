package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

	public Page<Loja> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return lojaRepository.findAll(pageRequest);
	}
}
