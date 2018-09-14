package com.nelioalves.cursomc.services;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public Cliente buscar(Integer id) {
		return repo.findById(id).orElseThrow(() ->
			new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Cliente.class.getName()));
	}
	
	public Cliente find(Integer id) {
		return repo.findById(id).orElseThrow(() ->
				new ObjectNotFoundException("Objecto nao encontrado: "+id + ", Tipo: "+ Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);// garante que nao foi passado algum id por engano e o service vai gerar um id para nós
		repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);// atualiza o objeto no banco
		return repo.save(newObj);
	}
	
	/**
	 * Atualiza somente nome e email no banco
	 * @param newObj - representa as informacoes que serao alteradas no banco
	 * @param obj - fornece os dados para alteracao
	 */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public void delete(Integer id) {
		Cliente obj = find(id);
		try {
			repo.delete(obj);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados!");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	/**
	 * Recebe uma categoriaDto e converte para Cliente
	 * @return
	 */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null)
			cli.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3() != null)
			cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		URI uri = s3Service.uploadFile(multipartFile);
		
		// Recupera o cliente com id 1 e o utiliza enquanto nao implementamos o controle de autenticacao
		Cliente cli = repo.findById(Integer.valueOf(1)).get();
		cli.setImageUrl(uri.toString()); // Salva a imagem como relacionado ao cliente
		repo.save(cli);
		
		return uri;
	}
}
