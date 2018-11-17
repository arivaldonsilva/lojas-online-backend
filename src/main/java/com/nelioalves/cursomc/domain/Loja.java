package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.nelioalves.cursomc.domain.enums.StatusLoja;

@Entity
public class Loja implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private Integer situacao; // 1 indica que está ativa, 2 significa que está inativa
	
	@OneToMany(mappedBy="loja")
	private List<Produto> produtos = new ArrayList<>();
	
	public Loja() {}

	public Loja(Integer id, String nome, StatusLoja situacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.situacao = situacao == null ? null : situacao.getCod();
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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public StatusLoja getSituacao() {
		return StatusLoja.toEnum(situacao);
	}

	public void setSituacao(StatusLoja situacao) {
		this.situacao = situacao.getCod();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loja other = (Loja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
