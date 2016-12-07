package To;

public class ListaOperacoesTO {

	String data, debitoCredito, numeroDocumento, valor, saldoFinal, tipo;
	
	public ListaOperacoesTO(String data, String debitoCredito, String numeroDocumento, String valor, String saldoFinal, String tipo) {
		setData(data);
		setDebitoCredito(debitoCredito);
		setNumeroDocumento(numeroDocumento);
		setSaldoFinal(saldoFinal);
		setValor(valor);
		setTipo(tipo);
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void setDebitoCredito(String debitoCredito) {
		this.debitoCredito = debitoCredito;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public void setSaldoFinal(String saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getData() {
		return data;
	}
	
	public String getDebitoCredito() {
		return debitoCredito;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public String getSaldoFinal() {
		return saldoFinal;
	}
	
	public String getValor() {
		return valor;
	}
	
	public String getTipo() {
		return tipo;
	}
}
