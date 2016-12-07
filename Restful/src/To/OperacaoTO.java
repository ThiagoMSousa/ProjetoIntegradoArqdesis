package To;

import java.util.Date;

public class OperacaoTO {
	
	protected int codigo, codigoCliente, tipo;
	protected Date data;
	protected double valor;

  	public OperacaoTO(int codigo, int codigoCliente, Date data, double valor, int tipo) {
  		setCodigo(codigo);
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
  	
  	public void setTipo(int tipo) {
		this.tipo = tipo;
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
}