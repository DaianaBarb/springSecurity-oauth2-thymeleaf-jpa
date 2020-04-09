package com.Test.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Test.Entities.CadastroEntity;
import com.Test.Repositories.CadastroRepository;

@Service

public class CadastroService {
	@Autowired
	private CadastroRepository c;
	String erro = "";

	public List<CadastroEntity> retornoPorNomeQuery(String nome) {
		List<CadastroEntity> lista = c.FindNome(nome);
		return lista;

	}

	public void Salvar(CadastroEntity entity) {
		try {
			c.saveAndFlush(entity);
			erro = "Cadastro Realizado com Sucesso";
		} catch (Exception e) {
			erro = "Cadastro  NÃO Realizado com Sucesso";
		}

	}

	public String getErro() {
		return erro;
	}

	public List<CadastroEntity> buscarTodos() {
		List<CadastroEntity> list = c.findAll();
		if (list.isEmpty()) {
			this.erro = "Lista Vazia";
			return list;
		} else
			return list;
	}

	public List buscarPorNome(String nome) {
		List<CadastroEntity> lista = c.findAll();
		List<CadastroEntity> lista2 = new ArrayList<>();

		for (CadastroEntity cadastro : lista) {
			if (cadastro.getNome().equals(nome)) {

				lista2.add(cadastro);
			}

		}
		if (lista2.isEmpty()) {
			this.erro = "Busca não Encontrada";
			return lista2;
		} else
			this.erro = "Busca Realizada";
		return lista2;

	}

	public void Excluir(Long id) {
		try {
			c.deleteById(id);
		} catch (Exception e) {
			this.erro = "Erro ao Excluir";
		}

	}

	public CadastroEntity Editar(Long id) {
		try {

			Optional<CadastroEntity> optional = c.findById(id);
//optional.get().setSenha(new BCryptPasswordEncoder());
			return optional.get();

		} catch (Exception e) {
			this.erro = "Erro ao editar Cadastro";
			return null;

		}
	}

}
