package com.vpaiva.cimagens.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.repository.UsuarioRepository;
import com.vpaiva.cimagens.service.impl.UsuarioServiceImpl;

@SpringJUnitConfig
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
		
	private UsuarioService usuarioService;
	
	@BeforeEach
	public void init() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}
	
	@Test
	public void save() throws Exception {
		String id = UUID.randomUUID().toString();
		String nome = "Jose";
		
		Usuario usuarioNaoPersistido = new Usuario(nome);
		
		Usuario usuarioRetornado = new Usuario(id, nome);
	
		given(usuarioRepository.save(usuarioNaoPersistido)).willReturn(usuarioRetornado);
		
		String usuarioSaved = usuarioService.save(usuarioNaoPersistido);
		
		assertThat(usuarioSaved).isEqualTo(usuarioSaved);
		
	}
	
	public void find() throws Exception {
		String id = UUID.randomUUID().toString();
		String nome = "Jose";
		
		Optional<Usuario> usuarioEsperado = Optional.of(new Usuario(id, nome));
		
		given(usuarioRepository.findById(id)).willReturn(usuarioEsperado);
		
		Optional<Usuario> usuarioRetornado = usuarioService.find(id);
		
		assertThat(usuarioRetornado).hasValue(usuarioEsperado.get());
		
	}
}
