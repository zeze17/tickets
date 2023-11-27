package com.tickets.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tickets.app.Repository.TicketRepository;
import com.tickets.app.Repository.UsuarioRepository;
import com.tickets.app.entity.Ticket;
import com.tickets.app.entity.Usuario;
import com.tickets.app.exception.NotFoundException;

@Controller 
@RequestMapping("/usuarios")
public class UsuarioWebController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping("/")
	public String usuarioListTemplate(Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "usuarios-list";
	}

	@GetMapping("/new")
	public String usuariosNewTemplate(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuarios-form";
	}

	@GetMapping("/edit/{id}")
	public String usuarioEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("usuario",
				usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado")));
		return "usuarios-form";
	}

	@PostMapping("/save")
	public String usuariosSaveProcess(@ModelAttribute("usuario") Usuario usuario) {
		if (usuario.getId().isEmpty()) {
			usuario.setId(null);
		}
		usuarioRepository.save(usuario);
		return "vista-puntaje";
	}

	@PostMapping("/salvar")
	public String usuariosSalvarProcess(@ModelAttribute("usuario") Usuario usuario) {
		if (usuario.getId().isEmpty()) {
			usuario.setId(null);
		}
		
		usuarioRepository.save(usuario);
		return "redirect:/usuarios/";
	}

	@GetMapping("/delete/{id}")
	public String usuariosDeleteProcess(@PathVariable("id") String id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		Ticket ticket = ticketRepository.findByUsuario(usuario);
		if (ticket != null) {
			ticketRepository.deleteById(ticket.getId());
		}
		usuarioRepository.deleteById(id);
		return "redirect:/usuarios/";
	}
	
	@GetMapping("/contrasena")
	public String registroTemplate(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "contrasena-usuario";
	}

	@GetMapping("/login")
	public String LoginTemplate(Model model) {
		return "login-usuario";
	}

	@PostMapping("/ingresar")
	public String login(@RequestParam("numDocumento") String numDocumento,
			@RequestParam("contrasena") String contrasena, Model model) {
		System.out.println("Documento: " + numDocumento + " Contrasena:" + contrasena);

		Usuario usuario = usuarioRepository.findByNumDocumento(numDocumento);
		if (usuario != null) {
			System.out.println(
					"Documento: " + usuario.getNumDocumento() + " Contraseña:" + usuario.getContrasena());
			return "redirect:/usuarios/resultado/" + numDocumento;
		} else {
			model.addAttribute("authenticationFailed", true);
			model.addAttribute("errorMessage", "No se encontró ningún usuario");
			return "login-usuario";
		}
	}

	@GetMapping("/resultado/{numDocumento}")
	public String usuarioResultTemplate(@PathVariable("numDocumento") String numDocumento, Model model) {
		Usuario usuario = usuarioRepository.findByNumDocumento(numDocumento);
		model.addAttribute("ticket", ticketRepository.findAllByUsuario(usuario));
		usuarioRepository.save(usuario);
		return "resultados-usuario"; 
	}

	@GetMapping("/detallado/{numDocumento}")
	public String usuarioDetailtTemplate(@PathVariable("numDocumento") String numDocumento, Model model) {
		Usuario usuario = usuarioRepository.findByNumDocumento(numDocumento);
		model.addAttribute("ticket", ticketRepository.findByUsuario(usuario));
		return "resultado-detallado"; 
	}

}
