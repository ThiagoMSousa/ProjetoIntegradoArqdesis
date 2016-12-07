package Unit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Unit {

	private static ArrayList<Model.Acesso> acessos;
	private static int indiceAcesso;
	
	public static void setAcessos(ArrayList<Model.Acesso> acessos) {
		Unit.acessos = acessos;
	}
	
	public static void setIndiceAcesso(int indiceAcesso) {
		Unit.indiceAcesso = indiceAcesso;
	}
	
	public static ArrayList<Model.Acesso> getAcessos() {
		return acessos;
	}
	
	public static int getIndiceAcesso() {
		return indiceAcesso;
	}
	
	public static String formatarSaldo(String saldo, ResourceBundle rbn) {
		String texto = rbn.getString("control.moeda") + " ";

		if (saldo.length() - saldo.indexOf(".") == 2){
			texto += saldo + "0";
		}else if (!saldo.contains(".") && !saldo.contains(",")){
			texto += saldo + ".00";
		}else{
			texto += saldo;
		}
		if (texto.contains("R$"))
			texto = texto.replace(".", ",");

		return texto;
	}
	
	public static String preparaValor(String valor, ResourceBundle rbn) {
		if (("ingles").equals(rbn.getString("idioma"))){
			valor = (Double.parseDouble(valor) / Double.parseDouble(rbn.getString("control.realParaDolar"))) + "";
		}else if (("espanhol").equals(rbn.getString("idioma"))){
			valor = (Double.parseDouble(valor) / Double.parseDouble(rbn.getString("control.realParaEuro"))) + "";
		}
		
		BigDecimal valorExato = new BigDecimal(valor);
		return valorExato.setScale(2, RoundingMode.HALF_UP) + "";
	}
	
	public static String pegaValor(String valor, ResourceBundle rbn) {
		if (("ingles").equals(rbn.getString("idioma"))){
			valor = (Double.parseDouble(valor) * Double.parseDouble(rbn.getString("control.realParaDolar"))) + "";
		}else if (("espanhol").equals(rbn.getString("idioma"))){
			valor = (Double.parseDouble(valor) * Double.parseDouble(rbn.getString("control.realParaEuro"))) + "";
		}
		
		BigDecimal valorExato = new BigDecimal(valor);
		return valorExato.setScale(2, RoundingMode.HALF_UP) + "";
	}
}
