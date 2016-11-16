package br.usjt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usjt.model.Banco;
import br.usjt.model.Conta;
import br.usjt.model.Movimentacao;
import br.usjt.to.MovimentacaoTO;

public class ListarMovimentacoes implements Comando {

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		List<MovimentacaoTO> list = new ArrayList<>();
		String message = "";
		String pagina = "";
		Integer numero = Integer.parseInt(req.getParameter("numero"));
		Integer senha = Integer.parseInt(req.getParameter("senha"));
		Integer agencia = Integer.parseInt(req.getParameter("agencia"));
		Banco banco = Banco.valueOf(req.getParameter("banco"));

		Movimentacao mov = new Movimentacao();
		Conta conta = new Conta().setBanco(banco).setAgencia(agencia).setNumero(numero).setSenha(senha);

		if (conta.exists()) {
			list = mov.getMovimentacoes(conta);
			req.setAttribute("movimentacoes", list);
			pagina = "listaMovimentacoes.jsp";
		} else {
			message = "Usuario Inexistente!";
			req.setAttribute("message", message);
			pagina = "erro.jsp";
		}
		return pagina;
	}

	

}
