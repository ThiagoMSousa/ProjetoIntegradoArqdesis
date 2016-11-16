package br.usjt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.usjt.to.UsuarioTO;


@WebFilter(urlPatterns = "/*")
public class FiltroDeAuditoria implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
					FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		UsuarioTO logado = (UsuarioTO) req.getSession().getAttribute("usuarioLogado");
		String uri = req.getRequestURI();

		if (logado == null) {
			System.out.println("Usuario não logado acessando " + uri);
		} else {
			System.out.println("Usuario " + logado.getUsuario() + " acessando " + uri);
		}
		
		chain.doFilter(request, response);
	
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	

	

}
