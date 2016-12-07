package Model;

import java.util.Date;

public class Transferencia extends Operacao{

	// Atributos
	private int codigoTransferencia;
	private String contaDestino, agenciaDestino;

	// Método construtor
	public Transferencia(int codigo, int codigoCliente, Date data, double valor,
		int tipo, int codigoTransferencia, String contaDestino, String agenciaDestino)
	{
		super(codigo, codigoCliente, data, valor, tipo);
		setCodigoTransferencia(codigoTransferencia);
		setContaDestino(contaDestino);
		setAgenciaDestino(agenciaDestino);
	}

	// Métodos modificadores
	public void setCodigoTransferencia(int codigoTransferencia) {
		this.codigoTransferencia = codigoTransferencia;
	}
	
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	public void setAgenciaDestino(String agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	// Métodos de acesso
	public int getCodigoTransferencia() {
		return codigoTransferencia;
	}
	
	public String getContaDestino() {
		return contaDestino;
	}
	
	public String getAgenciaDestino() {
		return agenciaDestino;
	}
	
  	public void cadastrarTransferencia(Model.Cliente cliente1, Model.Cliente cliente2, double valor) throws Exception{
		cliente1.alterarSaldo(valor, "-");

  		setCodigo(0);
  		setCodigoCliente(cliente1.getCodigo());
  		setData(new Date());
  		setValor(-valor);
  		setTipo(2);
		setCodigoTransferencia(0);
		setContaDestino(cliente2.getConta());
		setAgenciaDestino(cliente2.getAgencia());
		
		cadastrar();		
		
  		To.TransferenciaTO to = new To.TransferenciaTO(
  			getCodigoTransferencia(),
  			getCodigoTransferencia(),
  			getData(),
  			valor,
  			2,
  			getCodigoTransferencia(),
  			getContaDestino(),
  			getAgenciaDestino()
  		);
  		new Dao.OperacaoDAO().cadastrarTransferencia(to);
		
		cliente2.alterarSaldo(valor, "+");

  		setCodigo(0);
  		setCodigoCliente(cliente2.getCodigo());
  		setData(new Date());
  		setValor(+valor);
  		setTipo(3);
		setCodigoTransferencia(0);
		setContaDestino(cliente2.getConta());
		setAgenciaDestino(cliente2.getAgencia());
		
		cadastrar();
  	}
}
