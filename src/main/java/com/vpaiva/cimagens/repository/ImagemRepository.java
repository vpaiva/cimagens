package com.vpaiva.cimagens.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.vpaiva.cimagens.document.Imagem;

public interface ImagemRepository extends MongoRepository<Imagem, String> {

    @Query(value = "{'usuario.id': ?0}", fields = "{'usuario': 0}")
    List<Imagem> findPorUsuario(String idUsuario);
}
