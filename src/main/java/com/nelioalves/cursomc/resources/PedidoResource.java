package com.nelioalves.cursomc.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.services.PedidoService;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> listar(@PathVariable Integer id) {
		Pedido obj = service.buscar(id);
		//obj.orElseThrow(() -> new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Pedido.class.getName()));
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<?>> listarTodos() {
		List<Pedido> obj = service.buscarTodos();
		//obj.orElseThrow(() -> new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Pedido.class.getName()));
		return ResponseEntity.ok(obj);
	}
}
