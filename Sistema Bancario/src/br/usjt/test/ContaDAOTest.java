package br.usjt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.usjt.dao.ConnectionFactory;
import br.usjt.dao.ContaDAO;
import br.usjt.model.Banco;
import br.usjt.to.ContaTO;
import junit.framework.Assert;

public class ContaDAOTest {

	ContaDAO dao;
	ContaTO dadosConta, dadosConta2;

	@Before
	public void setUp() throws Exception {
		ConnectionFactory.openFactory();
		dao = new ContaDAO();
		dadosConta = new ContaTO(Banco.ITAU, 111, 2323, "José Pereira", new BigDecimal("90.00"));
	}

	@Test
	public void testNovaConta() {
		dao.novaConta(dadosConta);
		assertTrue((dadosConta.getNumero() != null));
	}

	@Test
	public void testGetNumeroContaFail() {
		try {
			ContaTO dadosConta2 = new ContaTO(Banco.ITAU, 111, 232323, "José Pereira", new BigDecimal("90"));
			dao.getNumeroConta(dadosConta2);
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(), "Conta não existente");
		}
	}

	@Test
	public void testUpdateSenha() {
		dao.getNumeroConta(dadosConta);
		dao.updateSenha(dadosConta, 9090);
		assertEquals(new Integer(9090), dadosConta.getSenha());
	}

	@Test
	public void testUpdateSaldo() {
		dao.getNumeroConta(dadosConta);
		BigDecimal novo = new BigDecimal("90.00");
		dao.updateSaldo(dadosConta, novo);
		assertEquals(dadosConta.getSaldo(), novo);
	}

	@Test
	public void testExcluir() {
		dadosConta2 = new ContaTO(Banco.CITYBANK, 123456, 2211, "Teste Excluir", new BigDecimal("90.00"));
		dao.novaConta(dadosConta2);
		assertEquals(true, dao.excluir(dadosConta2));
	}

	@Test
	public void testUpdateConta() {
		dadosConta2 = new ContaTO(Banco.CITYBANK, 123456, 2211, "Teste Excluir", new BigDecimal("90.00"));
		dao.novaConta(dadosConta2);
		dadosConta2.setTitular("TesteUpdate");
		dao.updateConta(dadosConta2);
		assertEquals("TesteUpdate", dadosConta2.getTitular());
	}
}
