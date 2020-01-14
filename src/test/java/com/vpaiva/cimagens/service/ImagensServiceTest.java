package com.vpaiva.cimagens.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.multipart.MultipartFile;

import com.vpaiva.cimagens.document.Imagem;
import com.vpaiva.cimagens.document.TipoImagem;
import com.vpaiva.cimagens.document.Usuario;
import com.vpaiva.cimagens.repository.ImagemRepository;
import com.vpaiva.cimagens.service.impl.ImagemServiceImpl;

@SpringJUnitConfig
public class ImagensServiceTest {
	
	@MockBean
	private ImagemRepository imagemRepository;

	@MockBean
	private ImagemUtils imagemUtils;
	
	private ImagemService imagemService;
	
	@BeforeEach
	public void init() {
		imagemService = new ImagemServiceImpl(imagemRepository, imagemUtils);
	}
	
	@Test
	public void save() throws Exception {
		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
		String id = UUID.randomUUID().toString();
		TipoImagem tipoImagem = TipoImagem.JPEG;
		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
		
		Imagem imagemEsperada = new Imagem(id, tipoImagem, foto, usuario);

		MultipartFile multipartFile = mock(MultipartFile.class);
		given(multipartFile.getBytes()).willReturn("simularImagem".getBytes());
		given(imagemUtils.getTipoArquivo(multipartFile)).willReturn(TipoImagem.JPEG);
		given(imagemRepository.save(any(Imagem.class))).willReturn(imagemEsperada);
		
		String idImagemRetornada = imagemService.save(usuario, multipartFile);
		
		assertThat(idImagemRetornada).isEqualTo(imagemEsperada.getId());
	}
	
//	@Test
//	public void saveTipoImagemInvalida() throws Exception {
//		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
//		String id = UUID.randomUUID().toString();
//		TipoImagem tipoImagem = TipoImagem.JPEG;
//		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
//		
//		Imagem imagem = new Imagem(tipoImagem, foto, usuario);
//		
//		Imagem imagemEsperada = new Imagem(id, tipoImagem, foto, usuario);
//		
//		given(imagemRepository.save(imagem)).willReturn(imagemEsperada);
//		
//		String idImagemRetornada = imagemService.save(imagem);
//		
//		assertThat(idImagemRetornada).isEqualTo(imagemEsperada.getId());
//	}
	
	@Test
	public void find() throws Exception {
		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
		String id = UUID.randomUUID().toString();
		TipoImagem tipoImagem = TipoImagem.JPEG;
		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
		
		Optional<Imagem> imagemEsperada = Optional.of(new Imagem(tipoImagem, foto, usuario));
		
		given(imagemRepository.findById(id)).willReturn(imagemEsperada);
		
		Optional<Imagem> imagemRetornado = imagemService.find(id);
		
		assertThat(imagemRetornado).hasValue(imagemEsperada.get());
	}
	
	@Test
    public void listByUsuario() throws Exception {
		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
		String id = UUID.randomUUID().toString();
		TipoImagem tipoImagem = TipoImagem.JPEG;
		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
		
		List<Imagem> imagemEsperada = new ArrayList<Imagem>();
		imagemEsperada.add(new Imagem(id, tipoImagem, foto, usuario));
		
		given(imagemRepository.findPorUsuario(usuario.getId())).willReturn(imagemEsperada);
		
		List<Imagem> imagemRetornado = imagemService.listByUsuario(usuario.getId());
		
		assertThat(imagemRetornado).isEqualTo(imagemEsperada);
    }
	
	@Test
	public void listAll() throws Exception {
		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
		String id = UUID.randomUUID().toString();
		TipoImagem tipoImagem = TipoImagem.JPEG;
		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
		
		List<Imagem> imagemEsperada = new ArrayList<Imagem>();
		imagemEsperada.add(new Imagem(id, tipoImagem, foto, usuario));
		
		given(imagemRepository.findAll()).willReturn(imagemEsperada);
		
		List<Imagem> imagemRetornado = imagemService.listAll();
		
		assertThat(imagemRetornado).isEqualTo(imagemEsperada);
	}
	
	@Test
	public void delete() throws Exception {
		Usuario usuario = new Usuario(UUID.randomUUID().toString(), "Jose");
		TipoImagem tipoImagem = TipoImagem.JPEG;
		Binary foto = new Binary(BsonBinarySubType.BINARY, "simularImagem".getBytes());
		
		Imagem imagem = new Imagem(tipoImagem, foto, usuario);
		
		doAnswer(invocation -> null).when(this.imagemRepository).delete(imagem);
		
		imagemService.delete(imagem);
		
	}
}
