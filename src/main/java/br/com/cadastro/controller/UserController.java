package br.com.cadastro.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.response.Response;
import br.com.cadastro.service.UsuarioService;


/**
 * 
 * @author felipe
 *
 *	Controller RESTful do usuário.
 */
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	/**
	 * Método responsável por criar um usuário.
	 * @return
	 */
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> create(HttpServletRequest request, @RequestBody Usuario usuario, BindingResult result){
		
		Response<Usuario> response = new Response<>();
		
		try {
			validateCreateUser(usuario, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			usuario.setPassword(passEncoder.encode(usuario.getPassword()));
			Usuario usuarioBD = usuarioService.createOrUpdate(usuario);
			response.setData(usuarioBD);
			
		} catch (DuplicateKeyException d) {
			response.getErrors().add("E-mail já registrado!");
			return ResponseEntity.badRequest().body(response);
		}
		catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Método responsável por validar os dados ao criar um usuário.
	 * @param user
	 * @param result
	 */
	private void validateCreateUser(Usuario usuario, BindingResult result) {
		
		if(usuario.getEmail() == null)
			result.addError(new ObjectError("Usuário", "E-mail não informado."));
	}
	
	/**
	 * Método responsável por atualizar um usuário.
	 * @param request
	 * @param user
	 * @param result
	 * @return
	 */
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> update(HttpServletRequest request, @RequestBody Usuario usuario, BindingResult result){
		Response<Usuario> response = new Response<>();
		
		try {
			validateUpdateUser(usuario, result);
			
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			usuario.setPassword(passEncoder.encode(usuario.getPassword()));
			Usuario userPersisted = usuarioService.createOrUpdate(usuario);
			response.setData(userPersisted);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método responsável por validar dados ao atualizar um usuário.
	 * @param user
	 * @param result
	 */
	private void validateUpdateUser(Usuario usuario, BindingResult result) {
		
		if(usuario.getId() == null)
			result.addError(new ObjectError("Usuário", "Id não informado."));
		
		if(usuario.getEmail() == null)
			result.addError(new ObjectError("Usuário", "E-mail não informado.."));
	}
	
	/**
	 * Método responsável por consultar um usuário através do ID.
	 * @param id
	 * @return
	 */
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> findById(@PathVariable("id") String id ){
		
		Response<Usuario> response = new Response<>();
		
		Usuario user = usuarioService.findById(id);
		if(user == null) {
			response.getErrors().add("Registro não encontrado, id: "+id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(user);
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método responsável por deletar um usuário através do ID.
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id ){
		
		Response<String> response = new Response<>();
		
		Usuario usuario = usuarioService.findById(id);
		if(usuario == null) {
			response.getErrors().add("Registro não encontrado, id: "+id);
			return ResponseEntity.badRequest().body(response);
		}
		
		usuarioService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Método responsável por consultar todos os usuários, utilizando paginação..
	 * @param page
	 * @param count
	 * @return
	 */
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Usuario>>> findAll(@PathVariable int page, @PathVariable int count ){
		
		Response<Page<Usuario>> response = new Response<>();
		Page<Usuario> users = usuarioService.findAll(page, count);
		response.setData(users);
		
		return ResponseEntity.ok(response);
	}
	
}
