package br.usjt.controller;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usjt.dao.UsuarioDAO;
import br.usjt.model.Banco;
import br.usjt.model.Conta;
import br.usjt.model.Usuario;
import br.usjt.to.ContaTO;
import br.usjt.to.UsuarioTO;

public class CriarLogin implements Comando {

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		
		
		String message = "";

		String usuario = req.getParameter("usuario");
		Integer senha = Integer.parseInt(req.getParameter("senha"));
		
		UsuarioTO user = new UsuarioTO();
		user.setSenha(senha);
		user.setUsuario(usuario);
		UsuarioDAO dao = new UsuarioDAO();
		
			if(dao.validar(user)){
				message = "Usuario existente";
			}else{
				dao.novoUsuario(user);
				message = "usuario registrado com sucesso";
			}
			req.setAttribute("message", message);
			return "login.jsp";
	}

}
