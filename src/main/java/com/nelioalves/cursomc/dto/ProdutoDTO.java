package com.nelioalves.cursomc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Double preco;
	
	private List<Categoria> categorias = new ArrayList<>(); // cria uma lista categoria para um produto
	
	private Loja loja;
	
	public ProdutoDTO() {}
	
	public ProdutoDTO(Produto obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
		loja = obj.getLoja();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
	
}
