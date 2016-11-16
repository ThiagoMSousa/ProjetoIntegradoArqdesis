package br.usjt.model;

import java.math.BigDecimal;

import br.usjt.dao.ContaDAO;
import br.usjt.to.ContaTO;

public class Conta {

	private Banco banco;
	private Integer numero;
	private Integer agencia;
	private Integer senha;
	private String titular;
	private BigDecimal saldo;
	private ContaDAO dao;

	public Conta(ContaTO conta) {
		this.numero = conta.getNumero();
		this.agencia = conta.getAgencia();
		this.senha = conta.getSenha();
		this.titular = conta.getTitular();
		this.banco = conta.getBanco();
		this.saldo = conta.getSaldo();

		dao = new ContaDAO();
	}

	public Conta() {
		dao = new ContaDAO();
	}

	public Integer getNumero() {
		return numero;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public Integer getSenha() {
		return senha;
	}

	public String getTitular() {
		return titular;
	}

	public Banco getBanco() {
		return banco;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public ContaDAO getDao() {
		return dao;
	}

	public Conta setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

	public Conta setAgencia(Integer agencia) {
		this.agencia = agencia;
		return this;
	}

	public Conta setSenha(Integer senha) {
		this.senha = senha;
		return this;
	}

	public Conta setTitular(String titular) {
		this.titular = titular;
		return this;
	}

	public Conta setBanco(Banco banco) {
		this.banco = banco;
		return this;
	}

	public Conta setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
		return this;
	}

	public Conta setDao(ContaDAO dao) {
		this.dao = dao;
		return this;
	}

	// CRUD METHODS
	public void novaConta() {
		ContaTO to = new ContaTO(this);
		dao.novaConta(to);
		this.setNumero(to.getNumero());
	}


	public void updateConta(ContaTO to) {
		dao.updateConta(to);
		this.setTitular(to.getTitular()); 
	}

	public void updateSenha(ContaTO to, int newSenha) {
		dao.updateSenha(to, newSenha);
	}

	public void updateSaldo(ContaTO to, BigDecimal valor) {
		dao.updateSaldo(to, valor);
	}

	private boolean excluir(ContaTO to) {
		return dao.excluir(to);
	}

	// ANOTHER DAO METHODS
	private void getContaFromDb(ContaTO to) {
		try {
			dao.getFromDb(to);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getSaldoFromDb() {
		ContaTO to = new ContaTO(this);
		dao.getSaldo(to);
		this.setSaldo(to.getSaldo());
	}

	public void doSaque(BigDecimal valor) {
		ContaTO contaTO = new ContaTO(this);
		dao.doSaque(contaTO, valor);
		this.setSaldo(contaTO.getSaldo());
	}
	
	public boolean exists(){
		ContaTO cTO =new ContaTO(this);
		boolean ret = dao.exists(cTO);
		this.numero = cTO.getNumero();
		return ret;
	}
}
