package br.com.cadastro.service.impl;

import java.util.List;

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
	public Pessoa findById(Long pessoaId) {
		
		return this.pessoaRepository.findOne(pessoaId);
	}

	@Override
	public void delete(Long pessoaId) {
		this.pessoaRepository.delete(pessoaId);
	}

	@Override
	public Page<Pessoa> findAll(int page, int count) {
		
		return this.pessoaRepository.findAll(new PageRequest(page, count));
	}

	@Override
	public Page<Pessoa> findByParameters(int page, int count, String nome, String email, String sexo, String cpf) {
		
		PageRequest pageRequest = new PageRequest(page, count);
		
		return this.pessoaRepository.findByNomeIgnoreCaseContainingAndEmailAndSexoAndCpfOrderByDataCadastroDesc(nome, email, sexo, cpf, pageRequest);
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
