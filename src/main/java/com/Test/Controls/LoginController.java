package com.Test.Controls;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Test.Entities.CadastroEntity;
import com.Test.Entities.LoginEntity;
import com.Test.Services.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService service;
	@RequestMapping({"/login","/Login"})
	public String login() {

		return "login";
	}
	@RequestMapping("/deslog")
	public String deslogar() {
		String usu = SecurityContextHolder.getContext().getAuthentication().getName().toString();
		LoginEntity entity=service.FindByLogin(usu);
		entity.setLogado(false);
		service.salvar(entity);
		return "redirect:/logout";
	}
	
	@RequestMapping("/cadastro")
	public String cadastro() {

		return "Cadastro";
	}
	
	@RequestMapping("/cadastro-tela-home")
	public String cadastroHome() {

		return "cadastro-tela-home";
	}
	
	@RequestMapping("/autorizar")
	@PreAuthorize("hasRole('ADMIN')")
	public String autorizar(ModelMap model) {
		String usu = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("ms", "Bem-vindo " + usu);
		model.addAttribute("logins", service.RetornaTodos());
		return "autorizar";
	}
	@RequestMapping("/excluirlogin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String excluirlogin(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.Excluir(id);
		attr.addFlashAttribute("msg", " Excluido com Sucesso");
		return "redirect:/home";
		// usar redirect para n aparecer o id na tela

	}
	@RequestMapping(value = "/cadastrarhome", method = RequestMethod.POST)
	public String cadastrarhome(LoginEntity login, RedirectAttributes re, @RequestParam("confirma") String senha) {
		LoginEntity log = service.FindByLogin(login.getLogin());
		// login.setLogado(false);

		if (log != null) {
			re.addFlashAttribute("msg",
					"Usuário já Existente, Porfavor recupere a senha. Se n for você, Crie um novo Login");
			return "redirect:/cadastro-tela-home";
		}

		if (!(login.getSenha().equals(senha))) {
			re.addFlashAttribute("msg", "As senhas devem ser identicas!");
			return "redirect:/cadastro-tela-home";
		}
		login.setSenha(new BCryptPasswordEncoder().encode(login.getSenha()));
		service.salvar(login);
		re.addFlashAttribute("msg", service.getErro());
		return "redirect:/cadastro-tela-home";
	}
	
		
		
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(LoginEntity login, RedirectAttributes re, @RequestParam("confirma") String senha) {

		LoginEntity log = service.FindByLogin(login.getLogin());
		// login.setLogado(false);

		if (log != null) {
			re.addFlashAttribute("msg",
					"Usuário já Existente, Porfavor recupere a senha. Se n for você, Crie um novo Login");
			return "redirect:/cadastro";
		}

		if (!(login.getSenha().equals(senha))) {
			re.addFlashAttribute("msg", "As senhas devem ser identicas!");
			return "redirect:/cadastro";
		}
		login.setSenha(new BCryptPasswordEncoder().encode(login.getSenha()));
		service.salvar(login);
		re.addFlashAttribute("msg", service.getErro());
		return "redirect:/cadastro";
	}

	
	@RequestMapping("/editaresalvarlogin")
	public String salvareEditarlogin( @RequestParam("senha") String senha,LoginEntity login, RedirectAttributes attr) {
		//login.setSenha(new BCryptPasswordEncoder().encode(login.getSenha()));
		
		 LoginEntity entity=service.FindByLogin(login.getLogin());
		 if(!(login.getSenha().equals(entity.getSenha()))) {
			 login.setSenha(new BCryptPasswordEncoder().encode(login.getSenha()));
		 
			service.Salvar(login);}
		 else {
			 
			 service.Salvar(login);
		 }
			attr.addFlashAttribute("msg", " Editado com Sucesso");
			return "redirect:/autorizar";

	}
	@RequestMapping("/editarlogin/{id}")
	public String preEditarlogin(@PathVariable("id") Long id, ModelMap model) { // recuperando o path id
		
		model.addAttribute("login", service.Editar(id)); 
		String usu = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("ms", "Bem-vindo " + usu);// cria uma variavel e coloca a busca dentro ddela para ser
															// enviado para a outra pagina
		return "editarlogin";
	}
}
