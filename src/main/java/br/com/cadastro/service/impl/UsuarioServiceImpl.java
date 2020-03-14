package br.com.cadastro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.repository.UsuarioRepository;
import br.com.cadastro.service.UsuarioService;

/**
 * 
 * @author felipe
 *	
 * Implementação do serviço relacionado ao usuário.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario findByEmail(String email) {
		
		return this.usuarioRepository.findByEmail(email);
	}

	@Override
	public Usuario createOrUpdate(Usuario usuario) {
		
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findById(String usuarioId) {
		
		return this.usuarioRepository.findOne(usuarioId);
	}

	@Override
	public void delete(String usuarioId) {
		
		this.usuarioRepository.delete(usuarioId);
		
	}

	@Override
	public Page<Usuario> findAll(int page, int count) {
		
		return this.usuarioRepository.findAll(new PageRequest(page, count));
	}
	
	
}
