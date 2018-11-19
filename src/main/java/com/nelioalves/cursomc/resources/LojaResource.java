package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Loja;
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
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="situacao", defaultValue="") Integer situacao) {
		Page<Loja> list = service.findPage(page, linesPerPage, orderBy, direction, situacao);
		Page<LojaDTO> listLojaDTO = list.map(loja -> new LojaDTO(loja));
		return ResponseEntity.ok().body(listLojaDTO);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody LojaDTO objDto){
		Loja obj = service.fromDTO(objDto);// converte o dto para um obj
		obj = service.insert(obj); // inclui o obj e o retorna já com código
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Loja> update(@Valid @RequestBody LojaDTO objDto, @PathVariable Integer id){
		Loja obj = service.fromDTO(objDto);// convete o dto em um objeto Loja
		obj.setId(id);// seta o id do objeto Loja usando o parametro pathvariable
		obj = service.update(obj); // atualiza o obj no banco
		return ResponseEntity.noContent().build();
	}
	
	// Exclui uma loja dado um id. Se a loja possuir produtos é gerada uma exceção de integridade. Se o id nao existir é gerada uma exceção de 
	// objeto nao encontrado
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
