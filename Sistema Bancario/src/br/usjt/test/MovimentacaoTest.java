package br.usjt.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import br.usjt.model.Banco;
import br.usjt.model.Movimentacao;
import br.usjt.model.TipoMovimentacao;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class MovimentacaoTest {
	Movimentacao mov, movCopia;
	MovimentacaoTO movTO;
	
	@Before
	public void setup(){
		Timestamp  date = Timestamp.valueOf(LocalDateTime.now());
		movTO = new MovimentacaoTO(new ContaTO(Banco.BRADESCO, 121212, 123), new BigDecimal("20.00") ,
				TipoMovimentacao.SAQUE, date);
		mov = new Movimentacao(movTO);
		movCopia = new Movimentacao(movTO);
	}
	
	@Test
	public void testCreate(){
		assertEquals(mov.getId(), movCopia.getId());
		assertEquals(mov.getFromConta(), movCopia.getFromConta());
		assertEquals(mov.getValor(), movCopia.getValor());
		assertEquals(mov.getTipoMovimentacao(), movCopia.getTipoMovimentacao());
		assertEquals(mov.getDate(), movCopia.getDate());
	}
	
	
}
