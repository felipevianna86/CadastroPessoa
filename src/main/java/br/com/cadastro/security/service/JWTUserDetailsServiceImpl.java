package br.com.cadastro.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.security.jwt.JwtUserFactory;
import br.com.cadastro.service.UsuarioService;

/**
 * 
 * @author felipe
 *
 * Classe para manipular interface do UserDetails.
 */
@Service
public class JWTUserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		Usuario user = usuarioService.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("No user found with e-mail %s", email));
		}
		
		return JwtUserFactory.create(user);
	}

}
