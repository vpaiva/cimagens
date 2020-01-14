package com.vpaiva.cimagens.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vpaiva.cimagens.document.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}
