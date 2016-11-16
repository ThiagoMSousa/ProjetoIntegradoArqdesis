package br.usjt.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import com.google.gson.Gson;

import br.usjt.dao.ContaDAO;
import br.usjt.dao.MovimentacaoDAO;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

@WebServlet("/api/movimentacoes")
public class MovimentacaoResource extends HttpServlet {

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
	/*
	 * fazer um serviço REST que retorne um arquivo JSON com o detalhe de uma
	 * determinada movimentacao
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			MovimentacaoTO movimentacao = new MovimentacaoDAO().getMovimentacao(Integer.valueOf(request.getParameter("id")));
			out.println(new Gson().toJson(movimentacao));
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"error\": \""+ e.getMessage()+"\"}");
			response.setStatus(404);
		}

	}
	
	@Override
	/*
	 * fazer um serviço REST que retorne um arquivo JSON com o extrato de uma
	 * determinada conta
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String formedJSON = request.getReader().lines().collect(Collectors.joining());
			ContaTO cTO = new Gson().fromJson(formedJSON, ContaTO.class);
			ContaTO contaTO = new ContaDAO().getFromDb(cTO.getNumero());
			List<MovimentacaoTO> movimentacoes = new MovimentacaoDAO().getMovimentacoes(contaTO);
			out.println(new Gson().toJson(movimentacoes));
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"error\": \""+ e.getMessage()+"\"}");
			response.setStatus(404);
		}

	}

	@Override
	/*
	 * fazer um serviço REST que aceite um arquivo JSON com os dados de uma
	 * transferência bancária e realizar a transferência.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			String formedJSON = request.getReader().lines().collect(Collectors.joining());
			MovimentacaoTO mTO = new Gson().fromJson(formedJSON, MovimentacaoTO.class);
			mTO.setDate(Timestamp.valueOf(LocalDateTime.now()));
			new MovimentacaoDAO().novaMovimentacao(mTO);
			new ContaDAO().doSaque(mTO.getFromConta(), mTO.getValor());
			out.println(new Gson().toJson(mTO.getFromConta()));
			response.setStatus(201);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"error\": \""+ e.getMessage()+"\"}");
			response.setStatus(400);
		}
	}
}
