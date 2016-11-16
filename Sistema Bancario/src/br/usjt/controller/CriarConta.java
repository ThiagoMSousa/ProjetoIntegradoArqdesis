package br.usjt.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usjt.model.Banco;
import br.usjt.model.Conta;
import br.usjt.to.ContaTO;

public class CriarConta implements Comando{

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) {
		String message = "";
		String pagina = "contaInfo.jsp";
		BigDecimal saldo =  new BigDecimal("0.00");
		String titular = req.getParameter("titular");
		Integer senha = Integer.parseInt(req.getParameter("senha"));
		Integer agencia = Integer.parseInt(req.getParameter("agencia"));
		Banco banco = Banco.valueOf(req.getParameter("banco"));
		
		Conta conta = new Conta().setBanco(banco).setAgencia(agencia).setSenha(senha).setTitular(titular).setSaldo(saldo);
		if(conta.exists()){
			message = "Conta Existente!!";
		}else{
			conta.novaConta();
			message = "Usuario Cadastrado com Sucesso!! <br> Anote seu numero:"+conta.getNumero();
		}
		ContaTO contaTO = new ContaTO(conta);
		req.setAttribute("message", message);
		req.setAttribute("contaTO", contaTO);
		return pagina;
	}

	
	

}
