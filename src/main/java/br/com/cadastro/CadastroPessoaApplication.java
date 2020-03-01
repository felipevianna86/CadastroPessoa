package br.com.cadastro;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@ComponentScan
@SpringBootApplication
public class CadastroPessoaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CadastroPessoaApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return super.configure(builder);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

}
