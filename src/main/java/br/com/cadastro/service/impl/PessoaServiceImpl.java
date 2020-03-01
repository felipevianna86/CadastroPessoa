package br.com.cadastro.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cadastro.entity.Pessoa;
import br.com.cadastro.repository.PessoaRepository;
import br.com.cadastro.repository.filter.PessoaFilter;
import br.com.cadastro.service.PessoaService;

/**
 * Classe que implementa o Service de Pessoa.
 * @author felipe
 *
 */
@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Override
	public Pessoa findByEmail(String email) {
		
		return this.pessoaRepository.findByEmail(email);
	}
	
	@Override
	public Pessoa findByCpf(String cpf) {
		
		return this.pessoaRepository.findByCpf(cpf);
	}

	@Override
	public Pessoa createOrUpdate(Pessoa pessoa) {
		
		return this.pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa findById(String pessoaId) {
		
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(pessoaId);
		
		if(pessoa.isPresent()) {
			return pessoa.get();
		}
		return null;
	}

	@Override
	public void delete(String pessoaId) {
		this.pessoaRepository.deleteById(pessoaId);		
	}

	@Override
	public Page<Pessoa> findAll(int page, int count) {
		
		return this.pessoaRepository.findAll(PageRequest.of(page, count));
	}

	@Override
	public Page<Pessoa> findByParameters(int page, int count, String nome, String email, String sexo, String cpf,
			String criadoPor) {
		
		return this.pessoaRepository.findByNomeIgnoreCaseContainingAndEmailAndSexoAndCpfAndCriadoPorOrderByDataCadastroDesc(nome, email, sexo, cpf, criadoPor, PageRequest.of(page, count));
	}

	@Override
	public List<Pessoa> filtrar(PessoaFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		String email = filtro.getEmail() == null ? "%" : filtro.getEmail();
		String cpf = filtro.getCpf() == null ? "%" : filtro.getCpf();
		
		return this.pessoaRepository.findByNomeIgnoreCaseContainingOrEmailContainingOrCpfContaining(nome, email, cpf);
	}

	@Override
	public List<Pessoa> buscarTodos() {
		
		return this.pessoaRepository.findAll();
	}
	

}
