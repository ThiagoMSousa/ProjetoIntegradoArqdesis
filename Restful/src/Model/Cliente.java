package Model;

import java.sql.SQLException;

public class Cliente {

	// Atributos
	private int codigo;
	private double saldo;
	private String nome, banco, agencia, conta;

	// Método construtor
	public Cliente(int codigo, String nome, String agencia, String conta, double saldo, String banco){
		setCodigo(codigo);
		setAgencia(agencia);
		setConta(conta);
		setSaldo(saldo);
		setNome(nome);
		setBanco(banco);
	}

	// Métodos modificadores
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
	
	// Métodos de acesso
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

	public static Cliente consultarCliente(int codigo) throws ClassNotFoundException, SQLException{
		return Dao.ClienteDAO.consultar(codigo);
	}	

	public void alterarSaldo(double valor, String tipo) throws Exception{
		To.ClienteTO to = new To.ClienteTO();
		to.setCodigo(getCodigo());
		if (tipo.equals("-")){
			to.setSaldo(getSaldo() - valor);
		}else if (tipo.equals("+")){
			to.setSaldo(getSaldo() + valor);
		}
		Dao.ClienteDAO.alterarSaldo(to);
		setSaldo(to.getSaldo());
	}
}