package com.nelioalves.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.domain.enums.StatusLoja;

public class LojaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório!")
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String nome;
	
	private Integer situacao;
	
	public LojaDTO() {}

	public LojaDTO(Loja loja) {
		super();
		this.id = loja.getId();
		this.nome = loja.getNome();
		this.situacao = loja.getSituacao().getCod();
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

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
	
	
	
}
