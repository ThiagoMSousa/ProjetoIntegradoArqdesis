package Model;

import java.util.Date;

public class Saque extends Operacao {

	// Método construtor
	public Saque(int codigo, int codigoCliente, Date data, double valor, int tipo) {
		super(codigo, codigoCliente, data, valor, tipo);
	}

	// Outros métodos
	public boolean validarValor(double valor) throws Exception {
		Model.Dispenser dispenser = new Model.Dispenser();
		return dispenser.validarValor(valor);
	}
	
	public int[] gerarCombinacao(int valor) throws Exception {
		Model.Dispenser dispenser = new Model.Dispenser();
		return dispenser.gerarCombinacao(valor);
	}

	public static String notasDisponiveis() throws Exception {
		return new Model.Dispenser().notasDisponiveis();
	}
}