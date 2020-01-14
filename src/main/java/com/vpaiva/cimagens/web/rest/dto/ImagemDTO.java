package com.vpaiva.cimagens.web.rest.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.vpaiva.cimagens.document.StatusUpload;
import com.vpaiva.cimagens.document.TipoImagem;
import com.vpaiva.cimagens.document.Usuario;

public class ImagemDTO implements Serializable {

	private static final long serialVersionUID = -2564631778437456438L;
	
	@Id
	private String id;
	
	private TipoImagem tipoImagem;
	
	private StatusUpload status;

	private String imagem;
	
	public ImagemDTO(String id, TipoImagem tipoImagem, StatusUpload status, String imagem) {
		this.id = id;
		this.tipoImagem = tipoImagem;
		this.status = status;
		this.imagem = imagem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoImagem getTipoImagem() {
		return tipoImagem;
	}

	public void setTipoImagem(TipoImagem tipoImagem) {
		this.tipoImagem = tipoImagem;
	}

	public StatusUpload getStatus() {
		return status;
	}

	public void setStatus(StatusUpload status) {
		this.status = status;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
