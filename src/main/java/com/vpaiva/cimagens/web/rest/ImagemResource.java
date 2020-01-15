package com.vpaiva.cimagens.web.rest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.Imagem;
import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.exception.TipoImagemNaoSuportado;
import com.vpaiva.cimagens.service.ImagemService;
import com.vpaiva.cimagens.service.UsuarioService;
import com.vpaiva.cimagens.web.rest.dto.ImagemDTO;
import com.vpaiva.cimagens.web.rest.result.RestResult;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/imagem")
public class ImagemResource {

    private final Logger log = LoggerFactory.getLogger(ImagemResource.class);

    private ImagemService imagemService;

    private UsuarioService usuarioService;

    public ImagemResource(ImagemService imagemService, UsuarioService usuarioService) {
        this.imagemService = imagemService;
        this.usuarioService = usuarioService;
    }

    @ApiOperation(value = "Fazer o upload de um arquivo com um id de usuário")
    @PostMapping(path = "/upload/{idUsuario}")
    public ResponseEntity<RestResult<String>> save(@PathVariable("idUsuario") String idUsuario, MultipartFile file) {
        log.debug("REST request to upload Imagem enviando no path o idUsuario: {}", idUsuario);

        boolean hasIdUsuarioEmpty = StringUtils.isEmpty(idUsuario);
        boolean hasMultipartFileNull = Objects.isNull(file);

        if (hasIdUsuarioEmpty || hasMultipartFileNull) {
            List<String> erros = new ArrayList<String>();

            if (hasIdUsuarioEmpty) {
                erros.add("Id não pode ser vazio.");
            }

            if (hasMultipartFileNull) {
                erros.add("Arquivo não pode ser vazio.");
            }

            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        Optional<Usuario> usuario = usuarioService.find(idUsuario);

        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            String idImagem = imagemService.save(usuario.get(), file);

            return ResponseEntity.ok(new RestResult<>(idImagem));
        } catch (TipoImagemNaoSuportado e) {
            List<String> erro = new ArrayList<String>();
            erro.add(e.getMessage());

            return ResponseEntity.badRequest().body(new RestResult<>(erro));
        }
    }

    @ApiOperation(value = "Buscar uma imagem pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RestResult<ImagemDTO>> find(@PathVariable("id") String id) {
        log.debug("REST request to find Imagem por id: {}", id);

        if (StringUtils.isEmpty(id)) {
            List<String> erros = new ArrayList<String>();
            erros.add("Id não pode ser vazio.");
            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        Optional<Imagem> imagem = imagemService.find(id);

        if (!imagem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RestResult<>(imagem.map(convertUsuarioDTO()).get()));
    }

    private Function<? super Imagem, ? extends ImagemDTO> convertUsuarioDTO() {
        return imag -> new ImagemDTO(imag.getId(), imag.getTipoImagem(), imag.getStatus(),
                Base64.getEncoder().encodeToString(imag.getImagem().getData()));
    }

    @ApiOperation(value = "Buscar todas as imagens do usuario")
    @GetMapping(path = "/usuario/{idUsuario}")
    public ResponseEntity<RestResult<List<ImagemDTO>>> listByUsuario(@PathVariable("idUsuario") String idUsuario) {
        log.debug("REST request to find Imagem por id Usuario: {}", idUsuario);

        if (StringUtils.isEmpty(idUsuario)) {
            List<String> erros = new ArrayList<String>();
            erros.add("IdUsuario não pode ser vazio.");
            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        boolean contemUsuario = usuarioService.contemUsuario(idUsuario);

        if (!contemUsuario) {
            return ResponseEntity.notFound().build();
        }

        List<ImagemDTO> imagens = imagemService.listByUsuario(idUsuario).stream().map(convertUsuarioDTO())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new RestResult<>(imagens));
    }

    @ApiOperation(value = "Buscar todas as imagens")
    @GetMapping
    public ResponseEntity<RestResult<List<ImagemDTO>>> listAll() {
        log.debug("REST request to find all Images por id");

        List<ImagemDTO> listAll = imagemService.listAll().stream().map(convertUsuarioDTO())
                .collect(Collectors.toList());

        RestResult<List<ImagemDTO>> restResult = new RestResult<>(listAll);

        return ResponseEntity.ok(restResult);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<RestResult<Void>> deleteImagem(@PathVariable("id") String id) {
        log.debug("REST request to delete Imagem por id: {}", id);

        if (StringUtils.isEmpty(id)) {
            List<String> erros = new ArrayList<String>();
            erros.add("Id não pode ser vazio.");
            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        Optional<Imagem> imagem = imagemService.find(id);

        if (!imagem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        imagemService.delete(imagem.get());

        return ResponseEntity.ok(new RestResult<>(null));
    }
}
