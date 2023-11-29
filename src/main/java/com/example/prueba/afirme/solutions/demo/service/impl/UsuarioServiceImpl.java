package com.example.prueba.afirme.solutions.demo.service.impl;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.prueba.afirme.solutions.demo.entity.Usuario;
import com.example.prueba.afirme.solutions.demo.repository.UsuarioRepository;
import com.example.prueba.afirme.solutions.demo.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Page<Usuario> findAll(Integer page, Integer numberElements) {
		Pageable pageable = PageRequest.of(page, numberElements,Sort.by("apellido").ascending());
		return usuarioRepository.findAll(pageable);
	}

	@Override
	public Usuario findById(Long id) {

		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {

		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario update(Long id, Usuario usuario) {
		Usuario usuarioExiste = usuarioRepository.findById(id).orElse(null);
		
		if(Objects.nonNull(usuarioExiste)) {
			usuarioExiste.setApellido(usuario.getApellido());
			usuarioExiste.setNombre(usuario.getNombre());
			usuarioExiste.setCorreoElectronico(usuario.getCorreoElectronico());
			usuarioExiste.setFechaNacimiento(usuario.getFechaNacimiento());
			return usuarioRepository.save(usuario);
		}
		
		return null;
	}

	@Override
	public Boolean delete(Long id) {

		Usuario usuarioExiste = usuarioRepository.findById(id).orElse(null);
		
		if(Objects.nonNull(usuarioExiste)) {
			usuarioRepository.delete(usuarioExiste);
			return true;
		}
		return false;
		
	}

	
}
