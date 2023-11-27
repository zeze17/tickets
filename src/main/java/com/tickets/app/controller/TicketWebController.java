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

import com.tickets.app.Repository.TicketRepository;
import com.tickets.app.Repository.UsuarioRepository;
import com.tickets.app.entity.Ticket;
import com.tickets.app.entity.Usuario;
import com.tickets.app.exception.NotFoundException;


@Controller 
@RequestMapping("/tickets")
public class TicketWebController {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/")
	public String ticketListTemplate(Model model) {
		model.addAttribute("Tickets", ticketRepository.findAll());
		return "tickets-list";
	}
	

	@GetMapping("/new")
	public String ticketsNewTemplate(Model model, Model usuarioModel) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarioModel.addAttribute("usuarios", usuarios);
		model.addAttribute("ticket", new Ticket());
		return "tickets-form";
	}

	@GetMapping("/edit/{id}")
	public String ticketEditTemplate(@PathVariable("id") String id, Model model, Model usuarioModel) {
		model.addAttribute("ticket",
				ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket no encontrado")));
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarioModel.addAttribute("usuarios", usuarios);
		return "tickets-form";
	}

	@PostMapping("/salvar")
	public String ticketsSaveProcess(@ModelAttribute("ticket") Ticket Ticket) {
		if (Ticket.getId().isEmpty()) {
			Ticket.setId(null);
		}
		ticketRepository.save(Ticket);
		return "redirect:/tickets/";
	}
	
	
	@GetMapping("/delete/{id}")
	public String ticketsDeleteProcess(@PathVariable("id") String id) {
		ticketRepository.deleteById(id);
		return "redirect:/tickets/";
	}
	
	@GetMapping("/borrar/{id}")
	public String usuariosBorrarProcess(@PathVariable("id") String id) {
		ticketRepository.deleteById(id);
		return "redirect:/tickets/niveles";
	}
}
