package br.usjt.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.usjt.model.Banco;
import br.usjt.model.Conta;
import br.usjt.to.ContaTO;

public class ContaTest {

	Conta c1, copia;
	ContaTO c1TO, copiaTO;

	@Before
	public void setup() {
		c1TO = new ContaTO(Banco.BANCO_DO_BRASIL, 121212, 2016, "Cleber", new BigDecimal("20.00"));
		copiaTO = new ContaTO(Banco.BANCO_DO_BRASIL, 121212, 2016, "Cleber", new BigDecimal("20.00"));
	}

	@Test
	public void testCreate() {
		c1 = new Conta(c1TO);
		copia = new Conta(copiaTO);
		assertEquals(c1.getBanco(), copia.getBanco());
		assertEquals(c1.getAgencia(), copia.getAgencia());
		assertEquals(c1.getNumero(), copia.getNumero());
		assertEquals(c1.getSenha(), copia.getSenha());
		assertEquals(c1.getTitular(), copia.getTitular());
		assertEquals(c1.getSaldo(), copia.getSaldo());
	}
}
