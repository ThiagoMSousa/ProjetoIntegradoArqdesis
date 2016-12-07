package To;

import java.util.Date;

public class DebitoTO {
	
	protected int codigo, codigoCliente, codigoOperacao, operadora, consumidor;
	protected double valor;
	protected Date data;

  	public DebitoTO(int codigo, Date data, int operadora, int consumidor, double valor, int codigoCliente, int codigoOperacao) {
  		setCodigo(codigo);
  		setData(data);
  		setOperadora(operadora);
  		setConsumidor(consumidor);
  		setValor(valor);
  		setCodigoCliente(codigoCliente);
  		setCodigoOperacao(codigoOperacao);
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
  	
  	public void setOperadora(int operadora) {
		this.operadora = operadora;
	}
  	
  	public void setCodigoOperacao(int codigoOperacao) {
		this.codigoOperacao = codigoOperacao;
	}
  	
  	public void setConsumidor(int consumidor) {
		this.consumidor = consumidor;
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
  	
  	public int getOperadora() {
		return operadora;
	}  	
  	
  	public int getCodigoOperacao() {
		return codigoOperacao;
	}
  	
  	public int getConsumidor() {
		return consumidor;
	}
}