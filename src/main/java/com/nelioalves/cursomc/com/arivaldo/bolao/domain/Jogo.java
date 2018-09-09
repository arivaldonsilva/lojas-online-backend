package com.nelioalves.cursomc.com.arivaldo.bolao.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Jogo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Date inicio;
	
	private Date encerramento;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="bolao_id")
	private Bolao bolao;

	public Jogo() {}

	public Jogo(Integer id, Date inicio, Date encerramento, Bolao bolao) {
		super();
		this.id = id;
		this.inicio = inicio;
		this.encerramento = encerramento;
		this.bolao = bolao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getEncerramento() {
		return encerramento;
	}

	public void setEncerramento(Date encerramento) {
		this.encerramento = encerramento;
	}

	public Bolao getBolao() {
		return bolao;
	}

	public void setBolao(Bolao bolao) {
		this.bolao = bolao;
	}
	
	
	
	
}
