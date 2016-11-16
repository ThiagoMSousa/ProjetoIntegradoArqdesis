package br.usjt.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usjt.model.Banco;
import br.usjt.model.Conta;

public class EfetuarSaque implements Comando{

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		String pagina = "";
		String message = "";

		Integer numero = Integer.parseInt(req.getParameter("numero"));
		Integer senha = Integer.parseInt(req.getParameter("senha"));
		Integer agencia = Integer.parseInt(req.getParameter("agencia"));
		Banco banco = Banco.valueOf(req.getParameter("banco"));
		String valorStr = req.getParameter("valor");
		
		BigDecimal valor = new BigDecimal(valorStr);
		Conta conta = new Conta().setBanco(banco).setAgencia(agencia).setSenha(senha).setNumero(numero);
		
		if (conta.exists()){
			try{
			conta.doSaque(valor);
			conta.getSaldoFromDb();
			req.setAttribute("conta", conta);
			req.setAttribute("valor", valor);
			message = "Saque efetuado com Sucesso!!";
			pagina = "saque.jsp";
			}catch(RuntimeException e){
				message = e.getMessage();
				pagina = "erro.jsp";
			}
		}else{
			message = "Conta Inexistente!!";
			pagina = "erro.jsp";
		}
		req.setAttribute("message", message);
		return pagina;
	}

	
}
