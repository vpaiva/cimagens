package com.vpaiva.cimagens.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.Imagem;
import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.exception.TipoImagemNaoSuportado;

public interface ImagemService {
	
	public Optional<Imagem> find(String id);
	
	public List<Imagem> listByUsuario(String idUsuario);
	
	public List<Imagem> listAll();

	public String save(Usuario usuario, MultipartFile file) throws TipoImagemNaoSuportado;

	public void delete(Imagem imagem);

}
