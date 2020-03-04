package br.com.cadastro.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.entity.Pessoa;
import br.com.cadastro.entity.Usuario;
import br.com.cadastro.enums.ProfileEnum;
import br.com.cadastro.response.Response;
import br.com.cadastro.security.jwt.JwtTokenUtil;
import br.com.cadastro.service.PessoaService;
import br.com.cadastro.service.UsuarioService;


/**
 * 
 * @author felipe
 *
 * Classe responsável por operações da Pessoa via API RESTful
 */
@RestController
@RequestMapping("/api/pessoa")
@CrossOrigin(origins = "*")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Método responsável por criar uma Pessoa
	 * @param request
	 * @param pessoa
	 * @param result
	 * @return
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Pessoa>> create(HttpServletRequest request, @RequestBody @Validated Pessoa pessoa, BindingResult result ){
		
		Response<Pessoa> response = new Response<>();
		
		try {
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			pessoa.setDataCadastro(new Date());
			
			Pessoa pessoaCriada = pessoaService.createOrUpdate(pessoa);
			response.setData(pessoaCriada);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Método responsável por editar uma Pessoa
	 * @param request
	 * @param pessoa
	 * @param result
	 * @return
	 */
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Pessoa>> update(HttpServletRequest request, @RequestBody Pessoa pessoa, BindingResult result ){
		
		Response<Pessoa> response = new Response<>();
		
		try {
			validateUpdatePessoa(pessoa, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}			
			
			Pessoa pessoaDB = pessoaService.findById(pessoa.getId());
			pessoa.setUltimaAtualizacao(new Date());
			pessoa.setDataCadastro(pessoaDB.getDataCadastro());
			
			Pessoa pessoaAtualizada = pessoaService.createOrUpdate(pessoa);
			response.setData(pessoaAtualizada);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
		}
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método responsável por buscar uma Pessoa por ID
	 * @param id
	 * @return
	 */
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Pessoa>> findById(@PathVariable("id") Long id){
		
		Response<Pessoa> response = new Response<>();
		
		Pessoa pessoa = pessoaService.findById(id);
		
		if(pessoa == null) {
			response.getErrors().add("Registro não encontrado, id: "+id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(pessoa);
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método responsável por buscar todas as Pessoas do sistema
	 * @param request
	 * @param page
	 * @param count
	 * @return
	 */
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Pessoa>>> findAll(HttpServletRequest request, @PathVariable("page") int page, @PathVariable("count") int count){
		
		Response<Page<Pessoa>> response = new Response<>();
		
		Page<Pessoa> pessoas = null;
		Usuario usuarioRequest = userFromRequest(request);

		if(usuarioRequest.getProfile().equals(ProfileEnum.ROLE_CUSTOMER))
			pessoas = pessoaService.findAll(page, count);
		
		response.setData(pessoas);
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Método responsável por deletar uma Pessoa pelo ID
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id){
		
		Response<String> response = new Response<>();
		
		Pessoa pessoa = pessoaService.findById(id);
		
		if(pessoa == null) {
			response.getErrors().add("Registro não encontrado, id:: "+id);
			return ResponseEntity.badRequest().body(response);
		}
		
		pessoaService.delete(pessoa.getId());		
		
		return ResponseEntity.ok(new Response<>());
	}
	
	/**
	 * Método responsável por buscar Pessoas através de diversos parâmetros.
	 * @param request
	 * @param page
	 * @param count
	 * @param nome
	 * @param email
	 * @param sexo
	 * @param cpf
	 * @return
	 */
	@GetMapping(value = "{page}/{count}/{number}/{title}/{status}/{priority}/{assigned}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Pessoa>>> findByParams(HttpServletRequest request, @PathVariable("page") int page,
			@PathVariable("count") int count, @PathVariable("nome") String nome, @PathVariable("email") String email, 
			@PathVariable("sexo") String sexo, @PathVariable("cpf") String cpf){
		
		nome = nome.equals("uninformed") ? "" : nome;
		email = email.equals("uninformed") ? "" : email;
		sexo = sexo.equals("uninformed") ? "" : sexo;	
		cpf = cpf.equals("uninformed") ? "" : cpf;
		
		Response<Page<Pessoa>> response = new Response<>();
		Page<Pessoa> pessoas = null;
				
		pessoas = pessoaService.findByParameters(page, count, nome, email, sexo, cpf);	
		
		response.setData(pessoas);
		return ResponseEntity.ok(response);
			
	}
	
	/**
	 * Valida se o ID da pessoa está sendo passado ao atualizá-la.
	 * @param pessoa
	 * @param result
	 */
	private void validateUpdatePessoa(Pessoa pessoa, BindingResult result) {
		if(pessoa.getId() > 0)
			result.addError(new ObjectError("Pessoa", "ID não informado"));
	}
	
	/**
	 * Resgata o usuário que está realizando a requisição através do Token.
	 * @param request
	 * @return
	 */
	public Usuario userFromRequest(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		String email = jwtTokenUtil.getUsernameFromToken(token);
		
		return usuarioService.findByEmail(email);
		
	}
		
}
