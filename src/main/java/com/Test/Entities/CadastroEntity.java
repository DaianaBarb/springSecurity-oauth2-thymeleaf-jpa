package com.Test.Entities;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity(name="Cadastronovo")
public class CadastroEntity implements Serializable {
	private static final long serialVersionUID =3862024889868951158L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
   private Long Id;
   @Column (name="nome")
   private String nome;
   @Column (name="email")
   private String email;
   @Column (name="senha")
   private String Senha;
   
   
public Long getId() {
	return Id;
}
public void setId(Long id) {
	Id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getSenha() {
	return Senha;
}
public void setSenha(String senha) {
	Senha = senha;
}
   
   
}
