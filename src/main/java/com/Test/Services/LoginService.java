package com.Test.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Test.Entities.CadastroEntity;
import com.Test.Entities.LoginEntity;
import com.Test.Repositories.LoginRepository;

@Service
public class LoginService {
	@Autowired
	private LoginRepository L;
	String Erro = null;

	public void AlterLogado(Boolean logado, String nome) {
		L.ALterLogado(logado, nome);
	}
	public void Salvar(LoginEntity entity) {
		try {
			L.saveAndFlush(entity);
			Erro = "Cadastro Realizado com Sucesso";
		} catch (Exception e) {
			Erro = "Cadastro  NÃO Realizado com Sucesso";}
		}


	public LoginEntity FindByLogin(String login) {
		try {
			return L.findByLogin(login);
		} catch (Exception e) {
			return null;
		}

	}
	
	public LoginEntity FindByNome(String nome) {
		try {
			return L.findByNome(nome);
		} catch (Exception e) {
			return null;
		}

	}

	public List<LoginEntity> RetornaTodos() {

		List<LoginEntity> lista = L.findAll();

		return lista;
	}

	public void salvar(LoginEntity entity) {
		try {
			L.saveAndFlush(entity);
			this.Erro = "Cadastrado com Sucesso!";
		} catch (Exception e) {
			this.Erro = "Erro ao Cadastrar, servior fora do ar";
		}

	}

	public String getErro() {
		return Erro;
	}

	public LoginEntity Editar(Long id) {
		try {

			Optional<LoginEntity> optional = L.findById(id);
//optional.get().setSenha(new BCryptPasswordEncoder());
			return optional.get();

		} catch (Exception e) {
			this.Erro = "Erro ao editar Cadastro";
			return null;

		}
	}


	public void Excluir(Long id) {
		try {
			L.deleteById(id);
		} catch (Exception e) {
			this.Erro = "Erro ao Excluir";
		}

	}
	

}
