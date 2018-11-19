package com.nelioalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.domain.enums.StatusLoja;
import com.nelioalves.cursomc.dto.LojaDTO;
import com.nelioalves.cursomc.repositories.LojaRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
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

	public Page<Loja> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, Integer situacao) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return lojaRepository.findBySituacao(situacao, pageRequest);
	}

	// Cria um objeto Loja a partir de um LojaDTO
	public Loja fromDTO(LojaDTO objDto) {
		return new Loja(objDto.getId(), objDto.getNome(), StatusLoja.toEnum(objDto.getSituacao()));
	}
	
	// Inclui um novo objeto Loja no repositório
	public Loja insert(Loja obj) {
		obj.setId(null);
		return lojaRepository.save(obj);
	}

	public Loja update(Loja obj) {
		Loja newObj = findById(obj.getId());// pesquisa se existe uma objeto com o codigo passado por parametro no banco. nao existindo gera excecao
		updateData(newObj, obj); // atualiza o objeto pesquisado com os valores vindo do obj
		return lojaRepository.save(newObj);
	}
	
	/**
	 * Atualiza somente nome e situacao no banco
	 * @param newObj - representa as informacoes que serao alteradas no banco
	 * @param obj - fornece os dados para alteracao
	 */
	private void updateData(Loja newObj, Loja obj) {
		newObj.setNome(obj.getNome());
		newObj.setSituacao(obj.getSituacao());
	}

	public void delete(Integer id) {
		Loja obj = findById(id);
		try {
			lojaRepository.delete(obj);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma loja que possui produtos!");
		}
	}
}
