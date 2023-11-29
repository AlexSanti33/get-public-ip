package com.example.prueba.afirme.solutions.demo.service;

import org.springframework.data.domain.Page;

import com.example.prueba.afirme.solutions.demo.entity.Usuario;

public interface IUsuarioService {

	public Page<Usuario> findAll(Integer page, Integer numberElements) ;
	
	public Usuario findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public Usuario update(Long id, Usuario usuario);
	
	public Boolean delete(Long id);
	
}
