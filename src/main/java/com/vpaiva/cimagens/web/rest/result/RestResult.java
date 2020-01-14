package com.vpaiva.cimagens.web.rest.result;

import java.util.List;

public class RestResult<T> {

	public T resultado;
	
	public List<String> erros;
	
	public RestResult(T resultado) {
		super();
		this.resultado = resultado;
	}
	
	public RestResult(List<String> erros) {
		super();
		this.erros = erros;
	}

	public T getResultado() {
		return resultado;
	}

	public void setResultado(T resultado) {
		this.resultado = resultado;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	
}
