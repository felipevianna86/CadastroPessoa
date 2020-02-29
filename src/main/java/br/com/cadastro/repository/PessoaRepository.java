package br.com.cadastro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.cadastro.entity.Pessoa;

/**
 * Classe que define o repository de Pessoa.
 * 
 * @author felipe
 *
 */
public interface PessoaRepository extends MongoRepository<Pessoa, String>, PagingAndSortingRepository<Pessoa, String> {
	
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
	 * @param criadoPor
	 * @param pages
	 * @return
	 */
	Page<Pessoa> findByNomeIgnoreCaseContainingAndEmailAndSexoAndCpfAndCriadoPorOrderByDataCadastroDesc(String nome, String email, String sexo, String cpf,
			String criadoPor, Pageable pages);

}
