package br.usjt.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/do")
public class FrontController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<String> comando = Optional.of(req.getParameter("comando"));
		
		if(comando.isPresent()){
			try {
				String caminho = "br.usjt.controller." + comando.get();
				Class<?> classe;
				classe = Class.forName(caminho);
				Comando instancia = (Comando) classe.newInstance();
				String pagina = instancia.executa(req, resp);
				RequestDispatcher dispatcher = req.getRequestDispatcher(pagina);
				dispatcher.forward(req, resp);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{
			throw new IllegalArgumentException("Comando nao presente, favor inserir comando apra execução"); 
		}
	}
}
