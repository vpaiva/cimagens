package com.vpaiva.cimagens.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.service.UsuarioService;
import com.vpaiva.cimagens.web.rest.dto.UsuarioDTO;
import com.vpaiva.cimagens.web.rest.result.RestResult;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Gravar um usuário", notes = "Gravar um usuário", response = UsuarioDTO.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário inserido com sucesso"),
        @ApiResponse(code = 400, message = "Dados de usuário inválidos na requisição")
    })
    @PostMapping
    public ResponseEntity<RestResult<String>> save(@NotEmpty @RequestBody UsuarioDTO usuario,
            BindingResult validationResult) {
        log.debug("REST request to save Usuario : {}", usuario);

        if (validationResult.hasErrors()) {
            List<String> erros = validationResult.getAllErrors().stream().map(erro -> erro.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        String idUsuario = usuarioService.save(new Usuario(usuario.getNome()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new RestResult<>(idUsuario));
    }

    @ApiOperation(value = "Buscar um usuário pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RestResult<Usuario>> find(@PathVariable("id") String id) {
        log.debug("REST request to find Usuario por id: {}", id);

        if (StringUtils.isEmpty(id)) {
            List<String> erros = new ArrayList<String>();
            erros.add("Id não pode ser vazio.");
            return ResponseEntity.badRequest().body(new RestResult<>(erros));
        }

        Optional<Usuario> usuario = usuarioService.find(id);

        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RestResult<>(usuario.get()));

    }
}
