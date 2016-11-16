package br.usjt.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usjt.dao.UsuarioDAO;
import br.usjt.to.UsuarioTO;


public class Login implements Comando {
	
	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		
		String usario = req.getParameter("usuario");
		Integer senha = Integer.parseInt(req.getParameter("senha"));

		UsuarioTO usuarioTO = new UsuarioTO(usario, senha);
		UsuarioDAO usuarioDAO = new UsuarioDAO();

			if (usuarioDAO.validar(usuarioTO)) {
				req.getSession().setAttribute("usuarioLogado", usuarioTO);
				return "index.jsp";
			} 
		req.setAttribute("message", "Login ou Senha invalidos");
		return "login.jsp";
	}

	
	
	
	
}
