package br.usjt.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.usjt.model.Banco;
import br.usjt.model.Conta;
import br.usjt.to.ContaTO;

public class ContaTOTest {

	ContaTO c1TO, copiaTO;
	
	@Before
	public void setup() {
		c1TO = new ContaTO(Banco.BANCO_DO_BRASIL, 123, 121212, 2016, "Cleber", new BigDecimal("20.00"));
		copiaTO = new ContaTO(Banco.BANCO_DO_BRASIL, 123, 121212, 2016, "Cleber", new BigDecimal("20.00"));
	}
	
	@Test
	public void testCreate(){
		assertEquals(c1TO.getBanco(), copiaTO.getBanco());
		assertEquals(c1TO.getAgencia(), copiaTO.getAgencia());
		assertEquals(c1TO.getNumero(), copiaTO.getNumero());
		assertEquals(c1TO.getSenha(), copiaTO.getSenha());
		assertEquals(c1TO.getTitular(), copiaTO.getTitular());
		assertEquals(c1TO.getSaldo(), copiaTO.getSaldo());
	}
	
	@Test
	public void testSetters(){
		copiaTO = new ContaTO(Banco.HSBC, 333, 112233, 4321, "CLEITON", new BigDecimal("120.00"));
		c1TO = new ContaTO()
				.setBanco(Banco.HSBC)
				.setAgencia(333)
				.setNumero(112233)
				.setSenha(4321)
				.setTitular("CLEITON")
				.setSaldo(new BigDecimal("120.00"));
		
		assertEquals(c1TO.getBanco(), copiaTO.getBanco());
		assertEquals(c1TO.getAgencia(), copiaTO.getAgencia());
		assertEquals(c1TO.getNumero(), copiaTO.getNumero());
		assertEquals(c1TO.getSenha(), copiaTO.getSenha());
		assertEquals(c1TO.getTitular(), copiaTO.getTitular());
		assertEquals(c1TO.getSaldo(), copiaTO.getSaldo());
	}
	
}
