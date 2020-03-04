package br.com.cadastro.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cadastro.entity.Pessoa;
import br.com.cadastro.repository.filter.PessoaFilter;
import br.com.cadastro.service.PessoaService;

/**
 * Controlador responsável por ações referentes à classe Pessoa.
 * @author felipe
 *
 */

@Controller
@RequestMapping("/pessoas")
public class TPessoaController {
		
	@Autowired
	private PessoaService pessoaService;
	
	private static final String CADASTRO = "cadastro_pessoa";
	
	private static final String PESQUISA = "pesquisa_pessoas";
	
	private static final String VISUALIZAR = "visualizar_pessoa";	
	
	/**
	 * Método que inicializa o cadastro de uma Pessoa.
	 * @return
	 */
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView model = new ModelAndView(CADASTRO);
		Pessoa pessoa = new Pessoa();
		pessoa.setSexo('M');
		model.addObject(pessoa);
		return model;
	}
	
	/**
	 * Método responsável por criar uma pessoa.
	 * @param pessoa
	 * @param errors
	 * @param attributes
	 * @return
	 */
	@PostMapping
	public String criar(@Validated Pessoa pessoa, Errors errors, RedirectAttributes attributes)  {
		if(errors.hasErrors()) 
			return CADASTRO;		
		
		try {
			
			if(pessoa.getId() !=null && pessoa.getId() > 0) {
				Pessoa pessoaDB = pessoaService.findById(pessoa.getId());
				pessoa.setDataCadastro(pessoaDB.getDataCadastro());
				pessoa.setUltimaAtualizacao(new Date());
			}
			else
				pessoa.setDataCadastro(new Date());
			
			pessoaService.createOrUpdate(pessoa);
			attributes.addFlashAttribute("mensagem", "Operação realizada com sucesso!");		
			return "redirect:/pessoas/novo";
		} catch (Exception e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
			return CADASTRO;
		}
	}
	
	/**
	 * Método criado pra editar uma pessoa.
	 * @param pessoa
	 * @return
	 */
	@GetMapping("{id}")
	public ModelAndView editar(@PathVariable("id") @Valid Pessoa pessoa) {		
		ModelAndView model = new ModelAndView(CADASTRO);
		
		try {
			model.addObject(pessoa);
		}catch (IllegalArgumentException e) {
			model.addObject(new Pessoa());
		}
		
		return model;
	}
	
	/**
	 * Método responsável por visualizar todos os dados de uma pessoa.
	 * @param pessoa
	 * @return
	 */
	@GetMapping("/visualizar/{id}")
	public ModelAndView visualizar(@PathVariable("id") @Valid Pessoa pessoa) {		
		ModelAndView model = new ModelAndView(VISUALIZAR);
		try {
			model.addObject(pessoa);		
		
		}catch (NumberFormatException e) {
			model.addObject(new Pessoa());
		}		
		catch (IllegalArgumentException e) {
			model.addObject(new Pessoa());
		}
		
		return model;
	}
	
	/***
	 * Método responsável por filtrar pessoas.
	 * Pesquisa de pessoas
	 */
	@GetMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") PessoaFilter filtro) {
		
		List<Pessoa> pessoas;
		if(filtro.getNome() == null  && filtro.getEmail() == null  && filtro.getCpf() == null)
			pessoas = pessoaService.buscarTodos();
		else
			pessoas = pessoaService.filtrar(filtro);
		ModelAndView model = new ModelAndView(PESQUISA);
		model.addObject("pessoas", pessoas);
		return model;
	}
	
	/**
	 * Método responsável por excluir uma pessoa.
	 * @param id
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		try {
		pessoaService.delete(id);		
		attributes.addFlashAttribute("mensagem", "Pessoa removida com sucesso!");
		
		return "redirect:/pessoas";
		}catch (Exception e) {
			attributes.addFlashAttribute("mensagemErro", e.getMessage());
			return "redirect:/pessoas";
		}
	}	
	
}
