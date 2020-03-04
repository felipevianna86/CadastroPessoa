package br.com.cadastro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cadastro.entity.Pessoa;

/**
 * Classe que define o repository de Pessoa.
 * 
 * @author felipe
 *
 */

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	/**
	 * Retorna uma pessoa através do e-mail passado por parâmetro
	 * 
	 * @param email
	 * @return
	 */
	Pessoa findByEmail(String email);
	
	/**
	 * 
	 * Retorna uma pessoa através do cpf passado por parâmetro
	 * 
	 * @param cpf
	 * @return
	 */
	Pessoa findByCpf(String cpf);
	
	/**
	 * Retorna uma lista de pessoas através dos parâmetros passados.
	 * 
	 * @param nome
	 * @param email
	 * @param sexo
	 * @param cpf
	 * @param pages
	 * @return
	 */
	Page<Pessoa> findByNomeIgnoreCaseContainingAndEmailAndSexoAndCpfOrderByDataCadastroDesc(String nome, String email, String sexo, String cpf,
			Pageable pages);
	
	/**
	 * Retorna uma lista de pessoas através dos parâmetros passados.
	 * @param nome
	 * @param email
	 * @param cpf
	 * @return
	 */
	List<Pessoa> findByNomeIgnoreCaseContainingOrEmailContainingOrCpfContaining(String nome, String email, String cpf);

}
