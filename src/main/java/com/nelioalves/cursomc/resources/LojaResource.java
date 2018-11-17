package com.nelioalves.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.LojaDTO;
import com.nelioalves.cursomc.services.LojaService;

@RestController
@RequestMapping(value="/lojas")
public class LojaResource {

	@Autowired
	private LojaService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<LojaDTO>> findAll(){
		List<Loja> list = service.findAll();
		Stream<LojaDTO> listLojaDto = list.stream().map(loja -> new LojaDTO(loja));
		return ResponseEntity.ok().body(listLojaDto.collect(Collectors.toList()));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loja> findById(@PathVariable Integer id){
		Loja loja = service.findById(id);
		return ResponseEntity.ok().body(loja);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<LojaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Loja> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<LojaDTO> listLojaDTO = list.map(loja -> new LojaDTO(loja));
		return ResponseEntity.ok().body(listLojaDTO);
	}
}
