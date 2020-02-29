package br.com.cadastro.service;

import org.springframework.data.domain.Page;

import br.com.cadastro.entity.Usuario;

/**
 * 
 * Interface de serviço do usuário.
 * 
 * @author felipe
 *
 */
public interface UsuarioService {
	
	/**
	 * Retorna um usuário através do e-mail passado por parâmetro.
	 * 
	 * @param email
	 * @return
	 */
	Usuario findByEmail(String email);
	
	/**
	 * Cria ou atualizar um usuário.
	 * 
	 * @param usuario
	 * @return
	 */
	Usuario createOrUpdate(Usuario usuario);
	
	/**
	 * Busca um usuário através do seu ID.
	 * @param usuarioId
	 * @return
	 */
	Usuario findById(String usuarioId);
	
	/**
	 * Remove um usuário através do seu ID.
	 * @param usuarioId
	 */
	void delete(String usuarioId);
	
	/**
	 * Busca todos os usuários.
	 * @param page
	 * @param count
	 * @return
	 */
	Page<Usuario> findAll(int page, int count);
}
