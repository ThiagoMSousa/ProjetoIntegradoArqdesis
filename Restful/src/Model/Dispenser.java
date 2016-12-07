package Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import To.DispenserTO;

public class Dispenser {
	
	// Atributos
	private int nota10, nota20, nota50;

	public Dispenser() throws Exception {
		carregarNotas();
	}

	// Metodos modificadores
	public void setNota10(int nota10) {
		this.nota10 = nota10;
	}

	public void setNota20(int nota20) {
		this.nota20 = nota20;
	}

	public void setNota50(int nota50) {
		this.nota50 = nota50;
	}

	// Metodos de acesso
	public int getNota10() {
		return nota10;
	}

	public int getNota20() {
		return nota20;
	}

	public int getNota50() {
		return nota50;
	}

	// Outros metodos
	private void carregarNotas() throws Exception{
		To.DispenserTO to = Dao.DispenserDAO.consultarNotas();
		setNota10(to.getNota10());
		setNota20(to.getNota20());
		setNota50(to.getNota50());
	}
	
	public static Map<String, Object> notasELimites(ResourceBundle rbn) throws Exception{
		Map<String, Object> itens;
		To.DispenserTO to = Dao.DispenserDAO.consultarNotas();

		itens = new LinkedHashMap<String, Object>();
		itens.put("quantidadeNota10", to.getNota10());
		itens.put("limiteNota10", 1000);
		itens.put("porcentagemNota10", ((100.0 * to.getNota10()) / 1000.0));
		itens.put("valorFormatadoNota10", Unit.Unit.formatarSaldo("10", rbn));

		itens.put("quantidadeNota20", to.getNota20());
		itens.put("limiteNota20", 1000);
		itens.put("porcentagemNota20", ((100.0 * to.getNota20()) / 1000.0));
		itens.put("valorFormatadoNota20", Unit.Unit.formatarSaldo("20", rbn));

		itens.put("quantidadeNota50", to.getNota50());
		itens.put("limiteNota50", 500);
		itens.put("porcentagemNota50", ((100.0 * to.getNota50()) / 500.0));
		itens.put("valorFormatadoNota50", Unit.Unit.formatarSaldo("50", rbn));
		
		return itens;
	}

	private void alterarNotas() throws Exception{
		To.DispenserTO to = new DispenserTO(getNota10(), getNota20(), getNota50());
		Dao.DispenserDAO.alterarNotas(to);
	}

	public boolean validarValor(double valor) {
		if (valor / 50 <= getNota50()) valor = valor % 50;
		if (valor / 20 <= getNota20()) valor = valor % 20;
		if (valor / 10 <= getNota10()) valor = valor % 10;
		if (valor == 0) return true;
		return false;
	}

	public int[] gerarCombinacao(int valor) throws Exception {
		int[] vetor = new int[3];
		if ((valor / 50 <= getNota50()) && (valor / 50 > 0)){
			setNota50(getNota50() - (valor / 50));
			vetor[0] = (valor / 50);
			valor = valor % 50;
		}
		if ((valor / 20 <= getNota20()) && (valor / 20 > 0)){
			setNota20(getNota20() - (valor / 20));
			vetor[1] = (valor / 20);
			valor = valor % 20;
		}
		if ((valor / 10 <= getNota10()) && (valor / 10 > 0)){
			setNota10(getNota10() - (valor / 10));
			vetor[2] = (valor / 10);
			valor = valor % 10;
		}
		alterarNotas();
		return vetor;
	}

	public String notasDisponiveis() {
		String retorno = "";
		if (getNota50() > 0) retorno += "50";
		if (getNota20() > 0) retorno += ", 20";
		if (getNota10() > 0) retorno += ", 10";
		if (retorno.startsWith(", ")) retorno = retorno.replaceFirst(", ", "");
		return retorno;
	}
}