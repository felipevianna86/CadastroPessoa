package br.com.cadastro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cadastro.entity.Usuario;
import br.com.cadastro.enums.ProfileEnum;
import br.com.cadastro.repository.UsuarioRepository;

@SpringBootApplication
public class CadastroPessoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroPessoaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		
		return args -> {
			initUsers(usuarioRepository, passwordEncoder);
		};
	}
	
	private void initUsers(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		
		String email = "felipe.vianna86@gmail.com";
		Usuario admin = new Usuario();
		admin.setEmail(email);
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		Usuario userDB = usuarioRepository.findByEmail(email);
		if( userDB == null )
			usuarioRepository.save(admin);
	}

}
