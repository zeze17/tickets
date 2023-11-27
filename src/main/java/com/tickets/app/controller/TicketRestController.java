package com.tickets.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.app.Repository.TicketRepository;
import com.tickets.app.entity.Ticket;
import com.tickets.app.exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/tickets")
public class TicketRestController {

	@Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable String id) {
        return ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
    }

    @PostMapping("/")
    public Ticket saveTicket(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.convertValue(body, Ticket.class);
        return ticketRepository.save(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.convertValue(body, Ticket.class);
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    @DeleteMapping("/{id}")
    public Ticket deleteTicket(@PathVariable String id) {
    	Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
    	ticketRepository.deleteById(id);
        return ticket;
    }
}
