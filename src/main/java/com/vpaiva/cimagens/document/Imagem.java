package com.vpaiva.cimagens.document;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Imagem {

	@Id
	private String id;
	
	private TipoImagem tipoImagem;
	
	private StatusUpload status;

	private Binary imagem;
	
	@DBRef
	private Usuario usuario;
	
	public Imagem() {
	}
	
	public Imagem(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Imagem(TipoImagem tipoImagem, Binary imagem, Usuario usuario) {
		this.tipoImagem = tipoImagem;
		this.imagem = imagem;
		this.usuario = usuario;
	}

	public Imagem(String id, TipoImagem tipoImagem, Binary imagem, Usuario usuario) {
		this(tipoImagem, imagem, usuario);
		this.id = id;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imagem other = (Imagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Binary getImagem() {
		return imagem;
	}

	public void setImagem(Binary imagem) {
		this.imagem = imagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public StatusUpload getStatus() {
		return status;
	}

	public void setStatus(StatusUpload status) {
		this.status = status;
	}
	
	
	
}
