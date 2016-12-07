package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Operacao {
	
	protected int codigo, codigoCliente, tipo;
	protected Date data;
	protected double valor;

  	public Operacao(int codigo, int codigoCliente, Date data, double valor, int tipo) {
  		setCodigo(codigoCliente);
  		setCodigoCliente(codigoCliente);
  		setData(data);
  		setValor(valor);
  		setTipo(tipo);
	}

  	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
  	
  	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

  	public void setData(Date data) {
		this.data = data;
	}
  	
  	public void setValor(double valor) {
		this.valor = valor;
	}
  	
  	public void setTipo(int tipo2) {
		this.tipo = tipo2;
	}

  	public int getCodigo() {
		return codigo;
	}
  	
  	public int getCodigoCliente() {
		return codigoCliente;
	}
  	
  	public Date getData() {
		return data;
	}
  	
  	public double getValor() {
		return valor;
	}
  	
  	public int getTipo() {
		return tipo;
	}

  	public int cadastrar() throws Exception{
  		To.OperacaoTO to = new To.OperacaoTO(
  			getCodigo(),
  			getCodigoCliente(),
  			getData(),
  			getValor(),
  			getTipo()
  		);
  		return new Dao.OperacaoDAO().cadastrar(to);
  	}

  	public static ArrayList<ArrayList<To.ListaOperacoesTO>> consultarExtrato(double saldo, int id, int dias, ResourceBundle rbn) throws Exception{
  		ArrayList<ArrayList<To.ListaOperacoesTO>> arrayRetorno = new ArrayList<ArrayList<To.ListaOperacoesTO>>();
  		ArrayList<To.OperacaoTO> arrayDados = new Dao.OperacaoDAO().consultarExtrato(id, dias);
  		int limite = arrayDados.size() % 10;
		while(arrayDados.size() > limite){
			arrayRetorno.add(preparaArray(new ArrayList<To.OperacaoTO>(arrayDados.subList(0, 10)), rbn, valorTotal(arrayDados, saldo)));
			arrayDados = new ArrayList<To.OperacaoTO>(arrayDados.subList(10, arrayDados.size()));
		}
		if (arrayDados.size() > 0) arrayRetorno.add(preparaArray(arrayDados, rbn, valorTotal(arrayDados, saldo)));
  		return arrayRetorno;
  	}
  	
  	private static ArrayList<To.ListaOperacoesTO> preparaArray(ArrayList<To.OperacaoTO> arrayDados, ResourceBundle rbn, double valorTotal){
  		ArrayList<To.ListaOperacoesTO> arrayRetorno = new ArrayList<To.ListaOperacoesTO>();
  		for (int i = 0; i < arrayDados.size(); i++){
  			valorTotal = valorTotal + arrayDados.get(i).getValor();
  			
  			String data = new SimpleDateFormat(rbn.getString("formatoData")).format(arrayDados.get(i).getData());
  			String debitoCredito = "-";
  			String valorOperacao = Unit.Unit.formatarSaldo(Unit.Unit.preparaValor(arrayDados.get(i).getValor() + "", rbn), rbn);
  			String saldoFinal = Unit.Unit.formatarSaldo(Unit.Unit.preparaValor(valorTotal + "", rbn), rbn);
  			String tipo = "";
  			
  			if (arrayDados.get(i).getValor() > 0){
  				debitoCredito = rbn.getString("view.telaConsultarExtrato.credito");
  			}else if (arrayDados.get(i).getValor() < 0){
  				debitoCredito = rbn.getString("view.telaConsultarExtrato.debito");
  			}
  			
  			if (arrayDados.get(i).getTipo() == 1){
  				tipo = rbn.getString("Dao.operacoes.1");
  			}else if (arrayDados.get(i).getTipo() == 2){
  				tipo = rbn.getString("Dao.operacoes.2");
  			}else if (arrayDados.get(i).getTipo() == 3){
  				tipo = rbn.getString("Dao.operacoes.3");
  			}else if (arrayDados.get(i).getTipo() == 4){
  				tipo = rbn.getString("Dao.operacoes.4");
  			}else if (arrayDados.get(i).getTipo() == 5){
  				tipo = rbn.getString("Dao.operacoes.5");
  			}
  			
  			To.ListaOperacoesTO operacao = new To.ListaOperacoesTO(
  				data,
  				debitoCredito,
  				arrayDados.get(i).getCodigo()+"",
  				valorOperacao,
  				saldoFinal,
  				tipo
  			);
  			arrayRetorno.add(operacao);
  		}
  		return arrayRetorno;
  	}
	
	private static double valorTotal(ArrayList<To.OperacaoTO> arrayDados, double saldo){
		for (int i = 0; i < arrayDados.size(); i++){
			saldo -= arrayDados.get(i).getValor();
		}
		return saldo;
	}

  	public static Map<String, Object> consultarOperacoesDiarias() throws Exception{
		Map<String, Object> itensFormatados = new LinkedHashMap<String, Object>();
		Map<String, Object> itensOriginais = new Dao.OperacaoDAO().consultarOperacoesDiarias();
		for (int i = 1; i < 6; i++){			
			Double formatado = (100 * Double.parseDouble(itensOriginais.get("totalOp" + i) + "")) / Double.parseDouble(itensOriginais.get("totalOp0") + "");
			BigDecimal valorExato = new BigDecimal(formatado);
			itensFormatados.put("porcentagem" + i, valorExato.setScale(3, RoundingMode.HALF_UP) + "%");
		}
  		return itensFormatados;
  	}
  	
  	public static ArrayList<To.ListaOperacoesTO> transformaListaSimples(ArrayList<ArrayList<To.ListaOperacoesTO>> arrayDados){
  		ArrayList<To.ListaOperacoesTO> retorno = new ArrayList<To.ListaOperacoesTO>();
  		for (ArrayList<To.ListaOperacoesTO> arrayAux : arrayDados){
  			for (To.ListaOperacoesTO to : arrayAux){
  				retorno.add(to);
  			}
  		}
  		return retorno;
  	}
}