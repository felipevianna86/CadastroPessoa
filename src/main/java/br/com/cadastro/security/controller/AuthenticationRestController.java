package br.com.cadastro.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.security.jwt.JwtAuthenticationRequest;
import br.com.cadastro.security.jwt.JwtTokenUtil;
import br.com.cadastro.security.model.CurrentUser;
import br.com.cadastro.service.UsuarioService;

/**
 * 
 * @author felipe
 *
 */
@RestController
@CrossOrigin(origins = "*")
@ResponseBody
public class AuthenticationRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = "/api/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException{
		
		String email = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();
		
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				email, password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		final String token = jwtTokenUtil.generateToken(userDetails);
		final Usuario user = usuarioService.findByEmail(email);
		
		user.setPassword(null);
		
		return ResponseEntity.ok(new CurrentUser(token, user));
	}
	
	@PostMapping(value = "/api/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
		
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(token);		
		final Usuario user = usuarioService.findByEmail(username);
		
		boolean canTokenBeRefreshed = jwtTokenUtil.canTokenBeRefreshed(token);
		
		if(canTokenBeRefreshed) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new CurrentUser(refreshedToken, user));
		}
		return ResponseEntity.badRequest().body(null);
	}
}
