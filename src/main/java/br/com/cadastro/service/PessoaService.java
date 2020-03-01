package br.com.cadastro.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.cadastro.entity.Pessoa;
import br.com.cadastro.repository.filter.PessoaFilter;

/**
 * Classe que define o service de Pessoa.
 * @author felipe
 *
 */
@Component
public interface PessoaService {

	/**
	 * Retorna uma pessoa através do e-mail passado por parâmetro.
	 * 
	 * @param email
	 * @return
	 */
	Pessoa findByEmail(String email);
	
	/**
	 * Retorna uma pessoa através do CPF passado por parâmetro.
	 * 
	 * @param email
	 * @return
	 */
	Pessoa findByCpf(String cpf);
	
	/**
	 * Cria ou atualiza uma pessoa.
	 * 
	 * @param pessoa
	 * @return
	 */
	Pessoa createOrUpdate(Pessoa pessoa);
	
	/**
	 * Busca uma pessoa através do seu ID.
	 * @param pessoaId
	 * @return
	 */
	Pessoa findById(String pessoaId);
	
	/**
	 * Remove uma pessoa através do seu ID.
	 * @param pessoaId
	 */
	void delete(String pessoaId);
	
	/**
	 * Busca todos as pessoas.
	 * @param page
	 * @param count
	 * @return
	 */
	Page<Pessoa> findAll(int page, int count);
	
	/**
	 * Busca pessoas utilizando parâmetros.
	 * @param page
	 * @param count
	 * @param nome
	 * @param email
	 * @param sexo
	 * @param cpf
	 * @param criadoPor
	 * @return
	 */
	Page<Pessoa> findByParameters(int page, int count, String nome, String email, String sexo, String cpf, String criadoPor);
	
	/**
	 * Filtrar pessoas utilizando parâmetros específicos.
	 * @param filtro
	 * @return
	 */
	List<Pessoa> filtrar(PessoaFilter filtro);
	
	/**
	 * Filtrar pessoas sem utilizar parâmetros específicos.
	 * @return
	 */
	List<Pessoa> buscarTodos();
	
}
