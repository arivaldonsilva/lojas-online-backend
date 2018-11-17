package com.nelioalves.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.domain.Pagamento;
import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.PagamentoComCartao;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.enums.Perfil;
import com.nelioalves.cursomc.domain.enums.StatusLoja;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.LojaRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	 
	@Autowired 
	BCryptPasswordEncoder pe;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private LojaRepository lojaRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Loja loja1 = new Loja(null, "Elma Calçados", StatusLoja.ATIVO);
		Loja loja2 = new Loja(null, "Parque das Palmeiras", StatusLoja.ATIVO);
		Loja loja3 = new Loja(null, "Nelio Artigos", StatusLoja.ATIVO);
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Vasos & Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.0, loja3);
		Produto p2 = new Produto(null, "Impressora", 800.0, loja3);
		Produto p3 = new Produto(null, "Mouse", 80.0, loja3);
		Produto p4 = new Produto(null, "Mesa de Escritório", 300.00, loja3);
		Produto p5 = new Produto(null, "Toalha", 50.00, loja3);
		Produto p6 = new Produto(null, "Colcha", 200.00, loja3);
		Produto p7 = new Produto(null, "TV true color", 1200.00, loja3);
		Produto p8 = new Produto(null, "Roçadeira", 800.00, loja3);
		Produto p9 = new Produto(null, "Abajour", 100.00, loja3);
		Produto p10 = new Produto(null, "Pendente", 180.00, loja3);
		Produto p11 = new Produto(null, "Shampoo", 90.00, loja3);
		
		loja3.getProdutos().addAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
		
		double margem = 1.7;
		
		Produto p12 = new Produto(null, "Vaso Malta Bowl 56 x 17 cm", 40.00 * margem, loja2);
		
		Produto p13 = new Produto(null, "Vaso Terra Cone 30 x 40 cm", 40.00 * margem, loja2);
		Produto p14 = new Produto(null, "Vaso Terra Cone 38 x 55 cm", 65.50 * margem, loja2);
		Produto p15 = new Produto(null, "Vaso Terra Cone 48 x 76 cm", 93.00 * margem, loja2);
		Produto p16 = new Produto(null, "Vaso Terra Cone 55 x 46 cm", 76.50 * margem, loja2);
		
		loja2.getProdutos().addAll(Arrays.asList(p12,p13,p14,p15,p16));
		
		int inicio = 17;
		int quantidadeProdutos = 51;
		Produto[] p = new Produto[quantidadeProdutos - inicio];
		for(int contador = 0; contador < quantidadeProdutos - inicio; contador++) {
			p[contador] = new Produto(null, "Produto "+(contador+inicio), 10.00, loja1);
			cat1.getProdutos().add(p[contador]);
			loja1.getProdutos().add(p[contador]);
			p[contador].getCategorias().add(cat1);
		}
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		cat6.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		p12.getCategorias().addAll(Arrays.asList(cat6));
		p13.getCategorias().addAll(Arrays.asList(cat6));
		p14.getCategorias().addAll(Arrays.asList(cat6));
		p15.getCategorias().addAll(Arrays.asList(cat6));
		p16.getCategorias().addAll(Arrays.asList(cat6));
	
		
		lojaRepository.saveAll(Arrays.asList(loja1, loja2, loja3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3, cat4,cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		produtoRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16));
		
		produtoRepository.saveAll(Arrays.asList(p));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Rio Branco", est1);
		Cidade c2 = new Cidade(null, "Tatuí", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria dendagua", "arivaldon.silva@gmail.com", "98782894604", TipoCliente.PESSOAFISICA
				,pe.encode("123"));
		cli1.getTelefones().add("3299942-6590");
		cli1.getTelefones().add("323551-6031");
		
		Cliente cli2 = new Cliente(null, "Ana Silva", "arivaldo.herbalife@gmail.com", "32652134205", TipoCliente.PESSOAFISICA
				,pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().add("323551-6034");
		
		Endereco e1 = new Endereco(null, "R Flores", "745", null, "centro", "36520000", cli1, c1);
		Endereco e2 = new Endereco(null, "Sementeira", null, null, "zona rural", "36520000", cli1, c2);
		Endereco e3 = new Endereco(null, "R Adolfo Frejat", "595", null, "Terra Firme", "28890000", cli2, c2);
				
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Pedido ped1 = new Pedido(null, dtf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, dtf.parse("30/10/2017 10:32"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, dtf.parse("20/10/2017 10:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
