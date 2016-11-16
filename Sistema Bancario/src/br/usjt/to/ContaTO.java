package br.usjt.to;

import java.math.BigDecimal;

import br.usjt.model.Banco;
import br.usjt.model.Conta;

public class ContaTO {

	private Banco banco;
	private Integer numero;
	private Integer agencia;
	private Integer senha;
	private String titular;
	private BigDecimal saldo;

	public ContaTO(Banco banco, Integer agencia, Integer numero, Integer senha, String titular, BigDecimal saldo) {
		this.banco = banco;
		this.agencia = agencia;
		this.numero = numero;
		this.senha = senha;
		this.titular = titular;
		this.saldo = saldo;
	}
	
	public ContaTO(Banco banco, Integer agencia, Integer senha, String titular, BigDecimal saldo) {
		this.banco = banco;
		this.agencia = agencia;
		this.senha = senha;
		this.titular = titular;
		this.saldo = saldo;
	}

	public ContaTO(Conta conta) {
		this.banco = conta.getBanco();
		this.agencia = conta.getAgencia();
		this.numero = conta.getNumero();
		this.senha = conta.getSenha();
		this.titular = conta.getTitular();
		this.saldo = conta.getSaldo();
	}

	public ContaTO(Banco banco, Integer numero, Integer agencia) {
		this.banco = banco;
		this.numero = numero;
		this.agencia = agencia;
	}

	public ContaTO() {
	}

	public Integer getNumero() {
		return numero;
	}

	public Integer getAgencia() {
		return agencia;
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

	public Integer getSenha() {
		return this.senha;
	}

	public ContaTO setNumero(Integer numero) {
		this.numero = numero;
		return this;
	}

	public ContaTO setAgencia(Integer agencia) {
		this.agencia = agencia;
		return this;
	}

	public ContaTO setSenha(Integer senha) {
		this.senha = senha;
		return this;
	}

	public ContaTO setTitular(String titular) {
		this.titular = titular;
		return this;
	}

	public ContaTO setBanco(Banco banco) {
		this.banco = banco;
		return this;
	}

	public ContaTO setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
		return this;
	}
}
