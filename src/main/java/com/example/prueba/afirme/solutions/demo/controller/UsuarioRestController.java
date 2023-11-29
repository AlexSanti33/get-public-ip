package com.example.prueba.afirme.solutions.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.afirme.solutions.demo.entity.Usuario;
import com.example.prueba.afirme.solutions.demo.service.IUsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios/{page}/{elements}")
	public ResponseEntity<?> findAll(@PathVariable Integer page,@PathVariable Integer elements){
		Page<Usuario>usuarios = null;
		try {
			usuarios=usuarioService.findAll(page,elements);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>("Error la buscar lista de usuario"+e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuarios,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Usuario usuario = null;
		try {
			usuario=usuarioService.findById(id);
			if (Objects.isNull(usuario)) {
				return new ResponseEntity<>("Usuario no existe",HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>("Error al buscar usuario"+e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuario,HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario,BindingResult result){
		Usuario usuarioPersistido = null;
		try {
			if (result.hasErrors()){
				List<String>errores = result.getFieldErrors().stream().map(err -> {
					return "El campo "+err.getField()+" "+err.getDefaultMessage();
				}).collect(Collectors.toList());
				return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
			}
			usuarioPersistido = usuarioService.save(usuario);

		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>("Error al buscar usuario"+e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuarioPersistido,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
		Usuario usuarioPersistido = null;
		try {
			if (result.hasErrors()){
				List<String>errores = result.getFieldErrors().stream().map(err -> {
					return "El campo "+err.getField()+" "+err.getDefaultMessage();
				}).collect(Collectors.toList());
				return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
			}
			usuarioPersistido = usuarioService.update(id,usuario);
			if (Objects.isNull(usuarioPersistido)) {
				return new ResponseEntity<>("Usuario no existe ",HttpStatus.BAD_REQUEST);
			}

		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>("Error al buscar usuario"+e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuarioPersistido,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Boolean eliminado = Boolean.TRUE;
		try {
			eliminado = usuarioService.delete(id);
			if(!eliminado) {
				return new ResponseEntity<>("Usuario no eliminado ",HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return new ResponseEntity<>("Error al eliminar usuario"+e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Usuario eliminado",HttpStatus.OK);

	}

}
