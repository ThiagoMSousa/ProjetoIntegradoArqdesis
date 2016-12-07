package Model;

import java.util.ArrayList;
import java.util.Date;

public class Debito {
	
	protected int codigo, codigoCliente, codigoOperacao, operadora, consumidor;
	protected double valor;
	protected Date data;

  	public Debito(int codigo, Date data, int operadora, int consumidor, double valor, int codigoCliente, int codigoOperacao) {
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

  	public void cadastrar() throws Exception{
  		To.DebitoTO to = new To.DebitoTO(
  			getCodigo(),
  			getData(),
  			getOperadora(),
  			getConsumidor(), 
  			getValor(),
  			getCodigoCliente(),
  			getCodigoOperacao()
  		);
  		new Dao.DebitoDAO().cadastrar(to);
  	}
  	
  	public ArrayList<Debito> consultarEmAberto() throws Exception{
  		return new Dao.DebitoDAO().consultarEmAberto();
  	}

  	public void pagar() throws Exception{
  		Model.Cliente cliente = Model.Cliente.consultarCliente(getCodigoCliente());
  		
  		if (cliente.getSaldo() >= getValor()){
			Model.Operacao operacao = new Model.Operacao(
				0,
				cliente.getCodigo(),
				new Date(),
				-getValor(),
				5
			);
			
			setCodigoOperacao(operacao.cadastrar());
			
			To.DebitoTO to = new To.DebitoTO(
				getCodigo(),
				getData(),
				getOperadora(),
				getConsumidor(), 
				getValor(),
				getCodigoCliente(),
				getCodigoOperacao()
			);
			
			Dao.DebitoDAO.pagar(to);
			cliente.alterarSaldo(getValor(), "-");
  		}
  	}
}