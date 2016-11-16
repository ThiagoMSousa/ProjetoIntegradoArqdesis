package br.usjt.model;
import br.usjt.to.ContaTO;
import br.usjt.to.UsuarioTO;

import java.sql.SQLException;

import br.usjt.dao.UsuarioDAO;

public class Usuario {

	private String usuario;
	private int senha;
	private UsuarioDAO dao;
	
	
	public Usuario(String usuario, int senha) {
		this.usuario = usuario;
		this.senha = senha;
		dao = new UsuarioDAO();
	}
	
	
	public Usuario() {
		dao = new UsuarioDAO();
	}


	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getSenha() {
		return senha;
	}
	public void setSenha(int senha) {
		this.senha = senha;
	}
	
	public void novoUsuario() {
		UsuarioTO to = new UsuarioTO(this);
		dao.novoUsuario(to);
	}
	
	

	}
