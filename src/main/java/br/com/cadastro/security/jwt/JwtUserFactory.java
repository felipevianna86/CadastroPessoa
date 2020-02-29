package br.com.cadastro.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.enums.ProfileEnum;

/**
 * 
 * @author felipe
 *
 *	Classe para transformar um usuário em um JwtUser.
 */

public class JwtUserFactory {
	
	private JwtUserFactory() {
		
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static JwtUser create(Usuario user) {
		
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getProfile()));
	}
	
	/**
	 * 
	 * @param profileEnum
	 * @return
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum){
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		
		return authorities;
	}
}
