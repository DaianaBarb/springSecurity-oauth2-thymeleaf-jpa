package com.Test.Security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Test.Entities.LoginEntity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// recurso de http que tem varios tipos de segurança
		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/cadastro", "/cadastrar", "/conferirLogin","/login").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().accessDeniedPage("/acesso-negado")// as demais paginas precisam de  autenticação
				.and().formLogin().loginPage("/login").permitAll().usernameParameter("login").passwordParameter("senha")
				.failureUrl("/login?error=true")
				// tem que passar pelo login para acessar as paginas que precisam de
				// autenticação
				.defaultSuccessUrl("/home").and().logout().permitAll().deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout=true").and()
				.sessionManagement().maximumSessions(1) // usuario so pode logar uma unica vez
				.maxSessionsPreventsLogin(true).expiredUrl("/login?expired=true").sessionRegistry(sessionRegistry());
		// acessar /logaut para sair da sessao

	}

 //registrar a sessao do usuario
	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

		// **em memoria**
		// .inMemoryAuthentication()
		// .withUser("daiana").password("123").roles("ADMIN"); // senha para acessar as
		// paginas
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/templates/**", "/materialize/**", "/style/**", "/cadastrar",
				"/cadastro", "styles.css");
		// paginas que devem ser ignoradas na autenticação

	}

	/*
	 * @Bean public void springSecurityFilterChain() {
	 * 
	 * }
	 */
}
