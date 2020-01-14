package com.vpaiva.cimagens.web.rest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -8128552889580929003L;

	@NotEmpty
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
