package com.Test.Security;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Test.Entities.LoginEntity;
import com.Test.Repositories.LoginRepository;
import com.Test.Services.LoginService;

import javassist.NotFoundException;
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
@Autowired
private LoginRepository repository;

@Autowired
LoginService service;
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		LoginEntity usuario = Optional.ofNullable(repository.findByLogin(login)).orElseThrow(()-> new UsernameNotFoundException("not found"));
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
	     
		usuario.setLogado(true);
	    service.salvar(usuario);
		
		return new User(usuario.getUsername(),usuario.getPassword(),usuario.isAdmin()?authorityListAdmin:authorityListUser);

	}
	
}
