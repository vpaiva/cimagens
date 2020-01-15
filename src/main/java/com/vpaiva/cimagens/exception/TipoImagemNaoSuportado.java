package com.vpaiva.cimagens.exception;

public class TipoImagemNaoSuportado extends Exception {

    private static final long serialVersionUID = -8257251223358790167L;

    public TipoImagemNaoSuportado() {
        super("O tipo da imagem não é suportado!");
    }

}
