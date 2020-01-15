package com.vpaiva.cimagens.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.TipoImagem;
import com.vpaiva.cimagens.exception.TipoImagemNaoSuportado;

public interface ImagemUtils {
    TipoImagem getTipoArquivo(MultipartFile file) throws TipoImagemNaoSuportado, IOException;
}
