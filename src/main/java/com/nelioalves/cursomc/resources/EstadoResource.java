package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.CidadeDTO;
import com.nelioalves.cursomc.dto.EstadoDTO;
import com.nelioalves.cursomc.services.CategoriaService;
import com.nelioalves.cursomc.services.CidadeService;
import com.nelioalves.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService serviceEstado;
	
	@Autowired
	private CidadeService serviceCidade;

//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
//		Categoria obj = service.find(id);
//		//obj.orElseThrow(() -> new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Categoria.class.getName()));
//		return ResponseEntity.ok(obj);
//	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAllEstados() {
		List<Estado> list = serviceEstado.findAll();
		Stream<EstadoDTO> listEstadoDTO = list.stream().map(estado -> new EstadoDTO(estado));
		return ResponseEntity.ok().body(listEstadoDTO.collect(Collectors.toList()));
	}
	
	@RequestMapping(value="/{estado_id}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAllCidades(@PathVariable Integer estado_id) {
		List<Cidade> list = serviceCidade.findByEstado(estado_id);
		Stream<CidadeDTO> listEstadoDTO = list.stream().map(cidade -> new CidadeDTO(cidade));
		return ResponseEntity.ok().body(listEstadoDTO.collect(Collectors.toList()));
	}
	
//	@RequestMapping(value="/page", method=RequestMethod.GET)
//	public ResponseEntity<Page<CategoriaDTO>> findPage(
//			@RequestParam(value="page", defaultValue="0") Integer page,
//			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
//			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
//			@RequestParam(value="direction", defaultValue="ASC") String direction) {
//		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
//		Page<CategoriaDTO> listCategoriaDTO = list.map(categoria -> new CategoriaDTO(categoria));
//		return ResponseEntity.ok().body(listCategoriaDTO);
//	}
}
