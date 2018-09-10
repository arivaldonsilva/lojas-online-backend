package com.nelioalves.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.nelioalves.cursomc.com.arivaldo.bolao.domain.Bolao;
import com.nelioalves.cursomc.com.arivaldo.bolao.domain.Jogo;
import com.nelioalves.cursomc.com.arivaldo.bolao.repositories.BolaoRepository;
import com.nelioalves.cursomc.com.arivaldo.bolao.repositories.JogoRepository;
import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.Pagamento;
import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.PagamentoComCartao;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
@EntityScan( basePackages = {"com.arivaldo.bolao","com.nelioalves.cursomc"} )
@EnableJpaRepositories(basePackages = {"com.arivaldo.bolao","com.nelioalves.cursomc"})
public class CursomcApplication implements CommandLineRunner {
	
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
	private BolaoRepository bolaoRepository;

	@Autowired
	private JogoRepository jogoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3, cat4,cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Rio Branco", est1);
		Cidade c2 = new Cidade(null, "Tatuí", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria dendagua", "maria@maria", "987828946-04", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().add("3299942-6590");
		cli1.getTelefones().add("323551-6031");
		
		Endereco e1 = new Endereco(null, "R Flores", "745", null, "centro", "36520000", cli1, c1);
		Endereco e2 = new Endereco(null, "Sementeira", null, null, "zona rural", "36520000", cli1, c2);
				
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
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
		
		Bolao bolao1 = new Bolao(null, "Bolao do Ernane", dtf.parse("01/09/2018 8:00"), 4, 5, 6);
		Bolao bolao2 = new Bolao(null, "Bolao do Dudu", dtf.parse("01/08/2018 8:00"), 6, 5, 10);
		
		Jogo jogo1 = new Jogo(null, dtf.parse("01/09/2018 8:00"), dtf.parse("05/09/2018 20:00"), bolao1);
		Jogo jogo2 = new Jogo(null, dtf.parse("06/09/2018 8:00"), null, bolao1);
		Jogo jogo3 = new Jogo(null, dtf.parse("01/08/2018 8:00"),  dtf.parse("05/08/2018 20:00"), bolao2);
		Jogo jogo4 = new Jogo(null, dtf.parse("06/08/2018 8:00"),  dtf.parse("12/08/2018 20:00"), bolao2);
		Jogo jogo5 = new Jogo(null, dtf.parse("13/08/2018 8:00"),  dtf.parse("20/08/2018 20:00"), bolao2);
		
		bolao1.getJogos().addAll(Arrays.asList(jogo1, jogo2));
		bolao2.getJogos().addAll(Arrays.asList(jogo3, jogo4, jogo5));
		
		bolaoRepository.saveAll(Arrays.asList(bolao1, bolao2));
		jogoRepository.saveAll(Arrays.asList(jogo1, jogo2, jogo3, jogo4, jogo5));
	}
}
