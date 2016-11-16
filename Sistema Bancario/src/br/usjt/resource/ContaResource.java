package br.usjt.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.usjt.dao.ContaDAO;
import br.usjt.dao.MovimentacaoDAO;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

@WebServlet("/api/conta")
public class ContaResource extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			super.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String formedJSON = request.getReader().lines().collect(Collectors.joining());
			ContaTO cTO = new Gson().fromJson(formedJSON, ContaTO.class);
			new ContaDAO().getFromDb(cTO);
			out.println(new Gson().toJson(cTO));
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"error\": \""+ e.getMessage()+"\"}");
			response.setStatus(404);
		}
	}
}
