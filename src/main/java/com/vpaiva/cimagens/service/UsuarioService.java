package com.vpaiva.cimagens.service;

import java.util.Optional;

import com.vpaiva.cimagens.document.Usuario;

public interface UsuarioService {
	
	public String save(Usuario usuario);
	
	public Optional<Usuario> find(String id);
	
	public boolean contemUsuario(String id);
}
