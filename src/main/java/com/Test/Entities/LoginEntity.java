package com.Test.Entities;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//ajuda na autenticação do spring useretails
@Entity(name="login")
public class LoginEntity implements Serializable, UserDetails {
	private static final long serialVersionUID =3862024889868951158L;	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name="nome")
private String nome;
@Column(name="login")
private String login;
@Column(name="senha")
private String senha;
@Column(name="logado")
private Boolean logado=false;
@Column(name="admin")
private boolean admin=true;

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	LoginEntity other = (LoginEntity) obj;
	if (login == null) {
		if (other.login != null)
			return false;
	} else if (!login.equals(other.login))
		return false;
	return true;
}
public boolean isAdmin() {
	return admin;
}
public void setAdmin(boolean admin) {
	this.admin = admin;
}
public Boolean getLogado() {
	return logado;
}
public boolean isLogado() {
	return logado;
}
public void setLogado(Boolean logado) {
	this.logado = logado;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getSenha() {
	return senha;
}
public void setSenha(String senha) {
	this.senha = senha;
}
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public String getPassword() {
	// TODO Auto-generated method stub
	return this.senha;
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return this.login;
}
@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}
}
