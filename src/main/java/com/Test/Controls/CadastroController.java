package com.Test.Controls;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Test.Entities.CadastroEntity;
import com.Test.Entities.LoginEntity;
import com.Test.Services.CadastroService;
import com.Test.Services.LoginService;


//@ServletComponentScan
//@WebFilter(urlPatterns ={"/home"})

@Controller
public class CadastroController {


	@Autowired
	private CadastroService service;

	// direciona para a pagina html editar
	



	// direciona para pagina html home
	@RequestMapping({ "/home", "/","/styles.css"})
	public String UploadPage(ModelMap model, RedirectAttributes att) {
		String usu = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("ms", "Bem-Vindo, " + usu);
		model.addAttribute("Cadastros", service.buscarTodos());
		return "home";
	}

	@RequestMapping({ "/acesso-negado" })
	public String negado() {

		return "acesso-negado";
	}

	// salva as informações no banco de dados de forma sigilosa com o metodo post
	// retornando do formulario um objeto da classe entidade CadastroEntity
	// ao final ele exibe uma mensagem de erro com o model na variavel mensagem que
	// e enviada para o html
	// ao mesmo tempo ele retorna a lista de usuarios cadastrados. salva e atualiza
	// imediatamente
	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public String salvar(Model model, CadastroEntity cadastro, RedirectAttributes attr,
			@RequestParam("confsenha") String senha) {
		if (cadastro.getSenha().equals(senha)) {
			
			service.Salvar(cadastro);
			attr.addFlashAttribute("msg", service.getErro());
			 model.addAttribute("Cadastros",service.buscarTodos());
			return "redirect:/home";
		} else
			attr.addFlashAttribute("msg", "As senhas devem ser iguais !");
		return "redirect:/home";
	}

	// exclui um usuario do banco de dados pegando a id da tabela com o
	// @Pathvariable
	// depois redireciona para home enviando uma mensagem de erro
	@RequestMapping("/excluir/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.Excluir(id);
		attr.addFlashAttribute("msg", " Excluido com Sucesso");
		return "redirect:/home";
		// usar redirect para n aparecer o id na tela

	}

	// edita o cadastro recebendo a id pelo @pathvariable busca o usuario no banco e
	// joga em uma variavel que e jogada pra outra pagina
	// na outra pagina aparece o cadastro a ser editado.
	@RequestMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) { // recuperando o path id
		model.addAttribute("Cadastro", service.Editar(id)); 
		String usu = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("ms", "Bem-vindo " + usu);// cria uma variavel e coloca a busca dentro ddela para ser
															// enviado para a outra pagina
		return "editar";
	}
	// o formulario envia para este metodo um objeto CadastroEntity, desse objeto e
	// retirado o id
	// o id e jogado no banco de dados e o metodo service.Editar() retorna uma
	// entidade cujo o id e igual o da entidade do formulario
	// logo em seguida e feita a edição da entidade e e salvo novamente no banco
	// fazendo o update

	@RequestMapping("/editaresalvar")
	public String salvareEditar(CadastroEntity cadastro, RedirectAttributes attr) {
		CadastroEntity entity = service.Editar(cadastro.getId());
		entity.setNome(cadastro.getNome());
		entity.setEmail(cadastro.getEmail());
		entity.setSenha(cadastro.getSenha());
		entity.setId(cadastro.getId());
		service.Salvar(entity);

		attr.addFlashAttribute("msg", " Editado com Sucesso");
		return "redirect:/home";

	}
	// este metodo faz a busca por nome na tabela de cadastros em outra tela
	// tbm e enviado uma mensagem caso o registro n for encontrado

	@RequestMapping("/buscar")
	public String busca(ModelMap model, @RequestParam("nomepesquisa") String nome) {
		String usu = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("ms", "Bem-vindo " + usu);
		model.addAttribute("Cadastros", service.retornoPorNomeQuery(nome));
		model.addAttribute("msg", service.getErro());
		return "home";
	}
}

