package To;

import java.util.Date;

public class TransferenciaTO extends OperacaoTO{

	private int codigoTransferencia;
	private String contaDestino, agenciaDestino;

	public TransferenciaTO(int codigo, int codigoCliente, Date data, double valor,
		int tipo, int codigoTransferencia, String contaDestino, String agenciaDestino)
	{
		super(codigo, codigoCliente, data, valor, tipo);
		setCodigoTransferencia(codigoTransferencia);
		setContaDestino(contaDestino);
		setAgenciaDestino(agenciaDestino);
	}

	public void setCodigoTransferencia(int codigoTransferencia) {
		this.codigoTransferencia = codigoTransferencia;
	}
	
	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	public void setAgenciaDestino(String agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public int getCodigoTransferencia() {
		return codigoTransferencia;
	}
	
	public String getContaDestino() {
		return contaDestino;
	}
	
	public String getAgenciaDestino() {
		return agenciaDestino;
	}
}
