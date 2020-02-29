package br.com.cadastro.service.impl;

import java.util.Optional;

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
		
		Optional<Usuario> usuario = this.usuarioRepository.findById(usuarioId);
		
		if(usuario.isPresent())
			return usuario.get();
		
		return null;
	}

	@Override
	public void delete(String usuarioId) {
		
		this.usuarioRepository.deleteById(usuarioId);
		
	}

	@Override
	public Page<Usuario> findAll(int page, int count) {
		
		return this.usuarioRepository.findAll(PageRequest.of(page, count));
	}
	
	
}
