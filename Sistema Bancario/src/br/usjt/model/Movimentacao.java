package br.usjt.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.usjt.dao.MovimentacaoDAO;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class Movimentacao {

	private Integer id;
	private ContaTO fromConta;
	private BigDecimal valor;
	private TipoMovimentacao tipoMovimentacao;
	private LocalDateTime date;
	private MovimentacaoDAO dao;

	public Movimentacao(MovimentacaoTO movTO) {
		fromConta = movTO.getFromConta();
		valor = movTO.getValor();
		tipoMovimentacao = movTO.getTipoMovimentacao();
		date = movTO.getDate().toLocalDateTime();
		dao = new MovimentacaoDAO();
	}

	public Movimentacao() {
		dao = new MovimentacaoDAO();
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

	public LocalDateTime getDate() {
		return date;
	}

	public Movimentacao setId(Integer id) {
		this.id = id;
		return this;
	}

	public Movimentacao setFromConta(ContaTO fromConta) {
		this.fromConta = fromConta;
		return this;
	}

	public Movimentacao setValor(BigDecimal valor) {
		this.valor = valor;
		return this;
	}

	public Movimentacao setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
		return this;
	}

	public Movimentacao setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}
	
	//DAO METHODS
	private boolean novaMovimentacao() {
		MovimentacaoTO mTO = new MovimentacaoTO(this);
		return dao.novaMovimentacao(mTO);
	}
	
	public List<MovimentacaoTO> getMovimentacoes(Conta conta) {
		ContaTO cTO = new ContaTO(conta);
		return dao.getMovimentacoes(cTO);
	}
}
