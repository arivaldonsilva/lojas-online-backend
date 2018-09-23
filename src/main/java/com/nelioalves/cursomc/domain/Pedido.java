package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyyy hh:mm")
	private Date instante;
	
	//@JsonManagedReference
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento = null;
	
	//@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	@Transient
	private static final Logger LOG = LoggerFactory.getLogger(Pedido.class);
	
	public Pedido() {
		LOG.info("Instanciando um pedido...");
	}

	public Pedido(Integer id, Date instante, /*Pagamento pagamento,*/ Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		//this.pagamento = pagamento;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		LOG.info("Setando o instante");
		this.instante = instante;
		LOG.info("Instante setado");
		LOG.info("Instante: "+instante);
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		LOG.info("Setando um pagamento");
		this.pagamento = pagamento;
		LOG.info("Pagamento setado");
		LOG.info("Pagamento: "+pagamento.getEstado());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		LOG.info("Setando cliente");
		this.cliente = cliente;
		LOG.info("Cliente setado");
		LOG.info("Cliente: "+cliente);
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido itemPedido : itens) {
			soma += itemPedido.getSubTotal();
		}
		return soma;
	}
	
	public Double getValorTotalLambida() {
		return itens.stream().mapToDouble(x -> x.getSubTotal()).sum();
	}

	//@Override
	public String imprime() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		Date inst = getInstante();
		//if(inst != null)
			builder.append(sdf.format(inst));
		
		builder.append(", Cliente: ");
		builder.append(getCliente());
		builder.append(", Situação do pagamento: ");
	//	System.out.println("pagamento: "+getPagamento().getEstado());
	//	 (obj == null) ? "null" : obj.toString();
		Pagamento pg = getPagamento();
		System.out.println("pg"+pg);
		if(pg.getEstado() != null) {
			builder.append(getPagamento().getEstado().getDescricao());
		}
		builder.append(", \nDetalhes:\n");
		for(ItemPedido ip : getItens()) {
			if(ip != null)
				builder.append(ip.toString());
		}
		builder.append("Valor total: ");
//		if(getValorTotal() != null)
//			builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}
	
	
}
