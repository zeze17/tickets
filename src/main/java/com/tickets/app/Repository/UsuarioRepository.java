package com.tickets.app.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tickets.app.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	Usuario findByNumDocumentoAndContrasena(String numDocumento, String contrasena);
	Usuario findByNumDocumento(String numDocumento);
}
