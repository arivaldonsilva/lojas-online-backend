package com.nelioalves.cursomc.com.arivaldo.bolao.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bolao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String descricao;
	
	private Date criadoEm;
	
	private Integer quantidadeSorteiosDiarios;
	
	private Integer quantidadeResultadosSorteio;
	
	private Integer quantidadeApostasUsuario;
	
	@OneToMany(mappedBy="bolao")
	private List<Jogo> jogos = new ArrayList<>();

	public Bolao() {
		super();
	}

	public Bolao(Integer id, String descricao, Date criadoEm, Integer quantidadeSorteiosDiarios,
			Integer quantidadeResultadosSorteio, Integer quantidadeApostasUsuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.criadoEm = criadoEm;
		this.quantidadeSorteiosDiarios = quantidadeSorteiosDiarios;
		this.quantidadeResultadosSorteio = quantidadeResultadosSorteio;
		this.quantidadeApostasUsuario = quantidadeApostasUsuario;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Integer getQuantidadeSorteiosDiarios() {
		return quantidadeSorteiosDiarios;
	}

	public void setQuantidadeSorteiosDiarios(Integer quantidadeSorteiosDiarios) {
		this.quantidadeSorteiosDiarios = quantidadeSorteiosDiarios;
	}

	public Integer getQuantidadeResultadosSorteio() {
		return quantidadeResultadosSorteio;
	}

	public void setQuantidadeResultadosSorteio(Integer quantidadeResultadosSorteio) {
		this.quantidadeResultadosSorteio = quantidadeResultadosSorteio;
	}

	public Integer getQuantidadeApostasUsuario() {
		return quantidadeApostasUsuario;
	}

	public void setQuantidadeApostasUsuario(Integer quantidadeApostasUsuario) {
		this.quantidadeApostasUsuario = quantidadeApostasUsuario;
	}

	public List<Jogo> getJogos() {
		return jogos;
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
		Bolao other = (Bolao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
	
}
