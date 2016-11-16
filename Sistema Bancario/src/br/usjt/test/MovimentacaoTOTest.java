package br.usjt.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import br.usjt.model.Banco;
import br.usjt.model.TipoMovimentacao;
import br.usjt.to.ContaTO;
import br.usjt.to.MovimentacaoTO;

public class MovimentacaoTOTest {
	MovimentacaoTO movTO, movTOCopia;
	
	@Before
	public void setup(){
		Timestamp  date = Timestamp.valueOf(LocalDateTime.now());
		movTO = new MovimentacaoTO(new ContaTO(Banco.BRADESCO, 121212, 123), new BigDecimal("20.00") ,
				TipoMovimentacao.SAQUE, date);
		
		movTOCopia = new MovimentacaoTO(new ContaTO(Banco.BRADESCO, 121212, 123), new BigDecimal("20.00") ,
				TipoMovimentacao.SAQUE, date);
	}
	
	@Test
	public void testCreate(){
		assertEquals(movTO.getId(), movTOCopia.getId());
		assertEquals(movTO.getFromConta().getBanco(), movTOCopia.getFromConta().getBanco());
		assertEquals(movTO.getFromConta().getNumero(), movTOCopia.getFromConta().getNumero());
		assertEquals(movTO.getFromConta().getAgencia(), movTOCopia.getFromConta().getAgencia());
		assertEquals(movTO.getValor(), movTOCopia.getValor());
		assertEquals(movTO.getTipoMovimentacao(), movTOCopia.getTipoMovimentacao());
		assertEquals(movTO.getDate(), movTOCopia.getDate());
	}
}
