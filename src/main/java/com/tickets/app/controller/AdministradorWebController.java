package com.tickets.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tickets.app.Repository.AdministradorRepository;
import com.tickets.app.entity.Administrador;
import com.tickets.app.exception.NotFoundException;


@Controller 
@RequestMapping("/administradores")
public class AdministradorWebController {
	@Autowired
	private AdministradorRepository administradorRepository;

	@GetMapping("/")
	public String AdministradorListTemplate(Model model) {
		model.addAttribute("administradores", administradorRepository.findAll());
		return "administradores-list";
	}

	@GetMapping("/new")
	public String administradoresNewTemplate(Model model) {
		model.addAttribute("administradores", new Administrador());
		return "administradores-form";
	}

	@GetMapping("/edit/{id}")
	public String AdministradorEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("administradores",
				administradorRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrador no encontrado")));
		return "administradores-form";
	}

	@PostMapping("/salvar")
	public String administradoresSaveProcess(@ModelAttribute("user") Administrador administrador) {
		if (administrador.getId().isEmpty()) {
			administrador.setId(null);
		}
		administradorRepository.save(administrador);
		return "redirect:/administradores/";
	}
	
	

	@GetMapping("/delete/{id}")
	public String administradoresDeleteProcess(@PathVariable("id") String id) {
		administradorRepository.deleteById(id);
		return "redirect:/administradores/";
	}

	@GetMapping("/registro")
	public String registroTemplate(Model model) {
		model.addAttribute("administrador", new Administrador());
		return "registro-administrador";
	}

	@GetMapping("/login")
	public String LoginTemplate(Model model) {
		return "login-administrador";
	}

	@PostMapping("/ingresar")
	public String login(@RequestParam("user") String user, @RequestParam("password") String password, Model model) {
		System.out.println("Usuario: " + user + " Contrasena:" + password);

		List<Administrador> administradorList = administradorRepository.findAll();
		System.out.println(administradorList.get(0).getUser());
		Administrador administrador = administradorRepository.findByUserAndPassword(user, password);
		if (administrador != null) {
			System.out.println("Usuario: " + administrador.getUser() + " Contrasena:" + administrador.getPassword());
			return "index"; 
		} else {
			model.addAttribute("authenticationFailed", true);
			model.addAttribute("errorMessage", "Usuario o contraseña incorrectos");
			return "login-administrador";
		}
	}

}
