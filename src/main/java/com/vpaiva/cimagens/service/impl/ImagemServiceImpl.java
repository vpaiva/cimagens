package com.vpaiva.cimagens.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.Imagem;
import com.vpaiva.cimagens.document.StatusUpload;
import com.vpaiva.cimagens.document.TipoImagem;
import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.exception.TipoImagemNaoSuportado;
import com.vpaiva.cimagens.repository.ImagemRepository;
import com.vpaiva.cimagens.service.ImagemService;
import com.vpaiva.cimagens.service.ImagemUtils;

@Service
public class ImagemServiceImpl implements ImagemService {

    private ImagemRepository imagemRepository;

    private ImagemUtils imagemUtils;

    public ImagemServiceImpl(ImagemRepository imagemRepository, ImagemUtils imagemUtils) {
        this.imagemRepository = imagemRepository;
        this.imagemUtils = imagemUtils;
    }

    @Override
    public Optional<Imagem> find(String id) {
        return imagemRepository.findById(id);
    }

    @Override
    public List<Imagem> listByUsuario(String idUsuario) {
        return imagemRepository.findPorUsuario(idUsuario);
    }

    @Override
    public List<Imagem> listAll() {
        return imagemRepository.findAll();
    }

    @Override
    public String save(Usuario usuario, MultipartFile file) throws TipoImagemNaoSuportado {
        Imagem imagem = new Imagem(usuario);
        imagem.setStatus(StatusUpload.EM_ANDAMENTO);

        Imagem savedImagem = imagemRepository.save(imagem);

        try {
            TipoImagem tipoImagem = imagemUtils.getTipoArquivo(file);
            Binary fileBinary = new Binary(BsonBinarySubType.BINARY, file.getBytes());
            savedImagem.setTipoImagem(tipoImagem);
            savedImagem.setStatus(StatusUpload.CONCLUIDO);
            savedImagem.setImagem(fileBinary);
        } catch (IOException e) {
            savedImagem.setStatus(StatusUpload.FALHA);
        }

        savedImagem = imagemRepository.save(savedImagem);

        return savedImagem.getId();
    }

    @Override
    public void delete(Imagem imagem) {
        imagemRepository.delete(imagem);
    }

}
