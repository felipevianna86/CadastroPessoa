package br.com.cadastro.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import br.com.cadastro.enums.ProfileEnum;


/**
 * 
 * @author felipe
 *
 *	Classe que representa um usuário do sistema.
 */
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	
	@NotBlank(message = "E-mail required")
	@Email(message = "E-mail invalid")
	private String email;
	
	@NotBlank(message = "Password required")
	@Size(min = 6)
	private String password;
	
	private ProfileEnum profile;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}
	
}
