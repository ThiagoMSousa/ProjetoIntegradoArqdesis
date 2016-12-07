package To;

public class ClienteTO {

	private int codigo;
	private double saldo;
	private String nome, banco, agencia, conta;

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setBanco(String banco) {
		this.banco = banco;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	
	public void setConta(String conta) {
		this.conta = conta;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getBanco() {
		return banco;
	}

	public String getAgencia() {
		return agencia;
	}
	
	public String getConta() {
		return conta;
	}
}