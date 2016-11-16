package br.usjt.to;

import br.usjt.model.Usuario;

public class UsuarioTO {
	
	private String usuario;
	private int senha;
	
	public UsuarioTO(){}
	
	public UsuarioTO(String usuario, int senha){
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public UsuarioTO(Usuario usuario){
		this.usuario = usuario.getUsuario();
		this.senha = usuario.getSenha();
		
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
	
	
	

}
