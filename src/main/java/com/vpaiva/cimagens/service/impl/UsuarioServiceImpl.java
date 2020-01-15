package com.vpaiva.cimagens.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.repository.UsuarioRepository;
import com.vpaiva.cimagens.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public String save(Usuario usuario) {
        Usuario saveUsuario = usuarioRepository.save(usuario);

        return saveUsuario.getId();
    }

    @Override
    public Optional<Usuario> find(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public boolean contemUsuario(String id) {
        return usuarioRepository.existsById(id);
    }

}
