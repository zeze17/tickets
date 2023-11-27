package com.tickets.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tickets.app.entity.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String>{
	Administrador findByUserAndPassword(String user, String password);
}
