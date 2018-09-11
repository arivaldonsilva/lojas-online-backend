package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		return repo.findById(id).orElseThrow(() ->
			new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Cliente.class.getName()));
	}
	
	public Cliente find(Integer id) {
		return repo.findById(id).orElseThrow(() ->
				new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Cliente.class.getName()));
	}
	
//	public Cliente insert(Cliente obj) {
//		obj.setId(null);// garante que nao foi passado algum id por engano e o service vai gerar um id para nós
//		return repo.save(obj);
//	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);// atualiza o objeto no banco
		return repo.save(newObj);
	}
	
	/**
	 * Atualiza somente nome e email no banco
	 * @param newObj - representa as informacoes que serao alteradas no banco
	 * @param obj - fornece os dados para alteracao
	 */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public void delete(Integer id) {
		Cliente obj = find(id);
		try {
			repo.delete(obj);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	/**
	 * Recebe uma categoriaDto e converte para Cliente
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
