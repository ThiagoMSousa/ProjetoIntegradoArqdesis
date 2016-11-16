package br.usjt.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import br.usjt.dao.MovimentacaoDAO;
import br.usjt.model.Banco;
import br.usjt.model.TipoMovimentacao;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class MovimentacaoDAOTest {

	MovimentacaoDAO dao;
	MovimentacaoTO movTO;
	ContaTO cTO;

	@Before
	public void setup() {
		dao = new MovimentacaoDAO();
		Timestamp date = Timestamp.valueOf(LocalDateTime.now());
		movTO = new MovimentacaoTO(new ContaTO(Banco.BRADESCO, 121212, 123), new BigDecimal("20.00"),
				TipoMovimentacao.SAQUE, date);
		cTO = new ContaTO(Banco.BRADESCO, 123, 121212, 2016, "Cleber", new BigDecimal("20.00"));
	}

	@Test
	public void testNovaMovimentacao() {
		assertTrue(dao.novaMovimentacao(movTO));
	}
}
