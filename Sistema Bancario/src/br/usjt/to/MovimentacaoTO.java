package br.usjt.to;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import br.usjt.model.Movimentacao;
import br.usjt.model.TipoMovimentacao;

public class MovimentacaoTO {
	private Integer id;
	private ContaTO fromConta;
	private BigDecimal valor;
	private TipoMovimentacao tipoMovimentacao;
	private Timestamp date;

	public MovimentacaoTO(Movimentacao mov) {
		this.id = mov.getId();
		this.fromConta = mov.getFromConta();
		this.valor = mov.getValor();
		this.tipoMovimentacao = mov.getTipoMovimentacao();
		this.date = Timestamp.valueOf(mov.getDate());
	}

	public MovimentacaoTO(ContaTO fromConta, BigDecimal valor,
			TipoMovimentacao saque, Timestamp date) {
		this.fromConta = fromConta; 
		this.valor = valor;
		this.tipoMovimentacao = saque;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public ContaTO getFromConta() {
		return fromConta;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public Timestamp getDate() {
		return date;
	}

	public MovimentacaoTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public MovimentacaoTO setFromConta(ContaTO fromConta) {
		this.fromConta = fromConta;
		return this;
	}

	public MovimentacaoTO setValor(BigDecimal valor) {
		this.valor = valor;
		return this;
	}

	public MovimentacaoTO setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
		return this;
	}

	public MovimentacaoTO setDate(Timestamp date) {
		this.date = date;
		return this;
	}
}