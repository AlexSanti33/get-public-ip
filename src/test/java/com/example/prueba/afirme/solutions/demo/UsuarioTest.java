package com.example.prueba.afirme.solutions.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.prueba.afirme.solutions.demo.entity.Usuario;
import com.example.prueba.afirme.solutions.demo.service.IUsuarioService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UsuarioTest {

	@Autowired
	private IUsuarioService usuarioService;

	@Test
	private void usuarioTest() {

		try {
			log.info("TestIniciado");

			log.info("creando usuario");
			Usuario usuario = new Usuario();
			usuario.setApellido("Santiago");
			usuario.setNombre("Alejandro");
			String fechaNacimiento = "1990-09-01";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(fechaNacimiento);
			usuario.setCorreoElectronico("santiago.jav27@gmail.com");
			usuario.setFechaNacimiento(date);
			usuarioService.save(usuario);
			log.info("Encontrar usuario");
			Usuario usuarioEncontrado = usuarioService.findById((long) 1);
			log.info("ActualizandoUsuario correo");
			usuarioEncontrado.setCorreoElectronico("santiago.jav37@afirmeSolutions.com");
			Usuario usuarioActualizado = usuarioService.update(usuarioEncontrado.getId(), usuarioEncontrado);
			log.info("Eliminado usuario");
			usuarioService.delete(usuarioActualizado.getId());
			log.info("TestTerminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
