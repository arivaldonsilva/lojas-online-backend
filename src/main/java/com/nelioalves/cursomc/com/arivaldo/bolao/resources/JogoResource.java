package com.nelioalves.cursomc.com.arivaldo.bolao.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.com.arivaldo.bolao.domain.Jogo;
import com.nelioalves.cursomc.com.arivaldo.bolao.services.JogoService;

@RestController
@RequestMapping(value="/jogos")
public class JogoResource {
	
	@Autowired
	private JogoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Jogo obj = service.buscar(id);
		//obj.orElseThrow(() -> new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Categoria.class.getName()));
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<?>> listarTodos() {
		List<Jogo> obj = service.buscarTodos();
		//obj.orElseThrow(() -> new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Categoria.class.getName()));
		return ResponseEntity.ok(obj);
	}
}
