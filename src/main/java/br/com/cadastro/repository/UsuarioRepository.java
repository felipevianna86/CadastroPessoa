package br.com.cadastro.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.cadastro.entity.Usuario;

/**
 * 
 * @author felipe
 *	
 *	Interface que armazena consultas relacionadas ao usuário do sistema.
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String>, PagingAndSortingRepository<Usuario, String>{
	
	/**
	 * Busca um usuário através do e-mail.
	 * @param email
	 * @return
	 */
	Usuario findByEmail(String email);

}