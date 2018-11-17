package com.nelioalves.cursomc.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.dto.ProdutoDTO;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		return repo.findById(id).orElseThrow(() ->
				new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Produto.class.getName()));
	}
	
	public List<Produto> buscarTodos() {
		return repo.findAll();
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
		
	}

	public @Valid Produto insert(@Valid Produto obj) {
		obj.setId(null);// garantir que nao será salvamento e sim insercao
		return repo.save(obj);
	}

	// Transforma o dto vindo da requisicao em um Produto com as respectivas categorias associadas
	public Produto fromDTO(@Valid ProdutoDTO objDto) {
		Produto prod = new Produto(objDto.getId(), objDto.getNome(), objDto.getPreco(), objDto.getLoja());
		for (Categoria cat: objDto.getCategorias()) {
			prod.getCategorias().add(cat);
		}
		//prod.getCategorias().add(objDto.getCategoria());// se uma categoria for preenchida ela será associada ao produto
		return prod;
	}
	
	/**
	 * copia o nome e preco de obj para newObj metodo auxiliar
	 * @param newObj
	 * @param obj
	 */
	private void updateData(Produto newObj, Produto obj) {
		newObj.setNome(obj.getNome());
		newObj.setPreco(obj.getPreco());
		newObj.getCategorias().removeAll(newObj.getCategorias());// exclui as categorias do produto atual
		newObj.getCategorias().addAll(obj.getCategorias());// inclui novas categorias recebidas por parametro
		newObj.setLoja(obj.getLoja());
	}
	
	/**
	 * Recebe um obj com dados que serao alterados para o banco. o código do item a ser alterado está no obj.
	 * @param obj
	 * @return
	 */
	public Produto update(Produto obj) {
		Produto prod = buscar(obj.getId()); // recupera o produto do id passado por parametro
		updateData(prod, obj);
		return repo.save(prod);
	}
}
