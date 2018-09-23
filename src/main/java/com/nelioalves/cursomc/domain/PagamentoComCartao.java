package com.nelioalves.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Transient
	private static final Logger LOG = LoggerFactory.getLogger(PagamentoComCartao.class);
	
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		LOG.info("Instanciando um pagamento com cartao herança");
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
		LOG.info("Setando o numero de parcelas");
		LOG.info("Parcelas: "+numeroDeParcelas);
	}
	
	
}
