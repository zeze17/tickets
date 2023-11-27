package com.tickets.app.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.tickets.app.entity.Ticket;
import com.tickets.app.entity.Usuario;

public interface TicketRepository extends MongoRepository<Ticket, String> {

	Ticket findByUsuario(Usuario usuario);

	List<Ticket> findAllByUsuario(Usuario usuario);

}
