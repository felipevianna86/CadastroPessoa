package br.com.cadastro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Classe responsável por remover o usuário da sessão.
 * @author felipe
 *
 */
@Controller
public class MainController {
	
	private static final String SOURCE = "source";

    @RequestMapping(value="/logmeout", method = RequestMethod.POST)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null)
	    	new SecurityContextLogoutHandler().logout(request, response, auth);
	    
	    return "redirect:/";
    }
    
    /**
     * Endpoint para acessar o codigo fonte do projeto.
     * @return
     */
    @GetMapping("/source")
	public String linkGit() {
		
		return SOURCE;
	}
    
}
