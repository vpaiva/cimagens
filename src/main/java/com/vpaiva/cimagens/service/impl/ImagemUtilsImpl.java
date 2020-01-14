package com.vpaiva.cimagens.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.TipoImagem;
import com.vpaiva.cimagens.exception.TipoImagemNaoSuportado;
import com.vpaiva.cimagens.service.ImagemUtils;

@Service
public class ImagemUtilsImpl implements ImagemUtils {
	
	public TipoImagem getTipoArquivo(MultipartFile file) throws TipoImagemNaoSuportado, IOException {
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(file.getBytes()));
		
		String mimeType = URLConnection.guessContentTypeFromStream(is);
		
		is.close();

		TipoImagem tipoImagem = TipoImagem.getImagem(mimeType);
		
		if(Objects.isNull(tipoImagem)) {
			throw new TipoImagemNaoSuportado();
		}
		
		return tipoImagem;
	}
}
